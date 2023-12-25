package com.crowdproj.resources.repo.tests

import com.crowdproj.resources.common.model.*
import com.crowdproj.resources.common.repo.DbResourceRequest
import com.crowdproj.resources.common.repo.IResourceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoRatingUpdateTest {
    abstract val repo: IResourceRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = CwpResourceId("resource-repo-update-not-found")
    protected val lockBad = CwpResourceLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = CwpResourceLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        CwpResource(
            id = updateSucc.id,
            otherResourceId = CwpOtherResourceId("1 updated"),
            scheduleId = CwpScheduleId("11 updated"),
            ownerId = CwpResourceUserId("owner-123"),
            lock =  initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = CwpResource(
        id = updateIdNotFound,
        otherResourceId = CwpOtherResourceId("1 update object not found"),
        scheduleId = CwpScheduleId("11 update object not found"),
        ownerId = CwpResourceUserId("owner-123"),
        lock = initObjects.first().lock,
    )

    private val reqUpdateConc by lazy {
        CwpResource(
            id = updateConc.id,
            otherResourceId = CwpOtherResourceId("1 update object not found"),
            scheduleId = CwpScheduleId("11 update object not found"),
            ownerId = CwpResourceUserId("owner-123"),
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateResource(DbResourceRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.otherResourceId, result.data?.otherResourceId)
        assertEquals(reqUpdateSucc.scheduleId, result.data?.scheduleId)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateResource(DbResourceRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateResource(DbResourceRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitResources("update") {
        override val initObjects: List<CwpResource> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}