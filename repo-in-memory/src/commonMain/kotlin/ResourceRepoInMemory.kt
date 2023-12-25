package com.crowdproj.resources.repo.inmemory

import com.benasher44.uuid.uuid4
import com.crowdproj.resources.common.helper.errorRepoConcurrency
import com.crowdproj.resources.common.model.*
import com.crowdproj.resources.common.repo.*
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  23.12.2023 12:44
 */

class ResourceRepoInMemory(
    initObjects: List<CwpResource> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IResourceRepository {

    private val cache = Cache.Builder<String, CwpResourceEntity>()
        .expireAfterWrite(ttl)
        .build()

    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(rating: CwpResource) {
        val entity = CwpResourceEntity(rating)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createResource(rq: DbResourceRequest): DbResourceResponse {
        val key = randomUuid()
        val rating = rq.resource.copy(id = CwpResourceId(key))
        val entity = CwpResourceEntity(rating)
        cache.put(key, entity)
        return DbResourceResponse(
            data = rating,
            isSuccess = true
        )
    }

    override suspend fun readResource(rq: DbResourceIdRequest): DbResourceResponse {
        val key = rq.id.takeIf { it != CwpResourceId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)?.let {
            DbResourceResponse(
                data = it.toInternal(),
                isSuccess = true,
            )
        } ?: resultErrorNotFound
    }

    override suspend fun updateResource(rq: DbResourceRequest): DbResourceResponse {
        val key = rq.resource.id.takeIf { it != CwpResourceId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.resource.lock.takeIf { it != CwpResourceLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newRating = rq.resource.copy()
        val entity = CwpResourceEntity(newRating)
        return mutex.withLock {
            val oldRating = cache.get(key)
            when {
                oldRating == null -> resultErrorNotFound
                oldRating.lock != oldLock -> DbResourceResponse(
                    data = oldRating.toInternal(),
                    isSuccess = false,
                    errors = listOf(
                        errorRepoConcurrency(
                            CwpResourceLock(oldLock),
                            oldRating.lock?.let { CwpResourceLock(it) })
                    )
                )

                else -> {
                    cache.put(key, entity)
                    DbResourceResponse(
                        data = newRating,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteResource(rq: DbResourceIdRequest): DbResourceResponse {
        val key = rq.id.takeIf { it != CwpResourceId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != CwpResourceLock.NONE }?.asString() ?: return resultErrorEmptyLock
        return mutex.withLock {
            val oldRating = cache.get(key)
            when {
                oldRating == null -> resultErrorNotFound
                oldRating.lock != oldLock -> DbResourceResponse(
                    data = oldRating.toInternal(),
                    isSuccess = false,
                    errors = listOf(
                        errorRepoConcurrency(CwpResourceLock(oldLock),
                            oldRating.lock?.let { CwpResourceLock(it) })
                    )
                )

                else -> {
                    cache.invalidate(key)
                    DbResourceResponse(
                        data = oldRating.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun searchResource(rq: DbResourceFilterRequest): DbResourcesResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.otherResourceId.takeIf { it != CwpOtherResourceId.NONE }?.let {
                    it.asString() == entry.value.otherResourceId
                } ?: true
            }
            .filter { entry ->
                rq.scheduleId.takeIf { it != CwpScheduleId.NONE }?.let {
                    it.asString() == entry.value.scheduleId
                } ?: true
            }
            .filter { entry ->
                rq.ownerId.takeIf { it != CwpResourceUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbResourcesResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {

        val resultErrorEmptyId = DbResourceResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                CwpResourceError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    title = "id-empty",
                    description = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbResourceResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                CwpResourceError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    title = "lock-empty",
                    description = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbResourceResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                CwpResourceError(
                    code = "not-found",
                    field = "id",
                    title = "not-found",
                    description = "Not Found"
                )
            )
        )
    }
}