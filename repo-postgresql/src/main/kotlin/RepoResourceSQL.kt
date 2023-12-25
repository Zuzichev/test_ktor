package com.crowdproj.resources.repo.postgresql

import com.benasher44.uuid.uuid4
import com.crowdproj.resources.common.model.*
import com.crowdproj.resources.common.repo.*
import com.crowdproj.resources.repo.postgresql.ResourceTable.fromRow
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  24.12.2023 02:09
 */

class RepoResourceSQL(
    properties: SqlProperties,
    initObjects: Collection<CwpResource> = emptyList(),
    private val randomUuid: () -> String = { uuid4().toString() },
) : IResourceRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url} ")
        }

        Database.connect(
            properties.url,
            driver,
            properties.user,
            properties.password
        )

        transaction {
            SchemaUtils.drop(ResourceTable)
            SchemaUtils.create(ResourceTable)
        }
    }

    override suspend fun createResource(rq: DbResourceRequest): DbResourceResponse = transaction {
        val result: CwpResource =
            ResourceTable.insert { toRow(it, rq.resource, randomUuid) }
                .resultedValues?.singleOrNull()?.let { fromRow(it) } ?: return@transaction DbResourceResponse.errorSave
        DbResourceResponse.success(result)
    }

    override suspend fun readResource(rq: DbResourceIdRequest): DbResourceResponse = transaction {
        val result: CwpResource =
            ResourceTable.select { ResourceTable.id eq rq.id.asString() }
                .singleOrNull()?.let { fromRow(it) } ?: return@transaction DbResourceResponse.errorNotFound
        DbResourceResponse.success(result)
    }

    // без проверки на lock
    override suspend fun updateResource(rq: DbResourceRequest): DbResourceResponse = transaction {
        val id = rq.resource.id
        if (id == CwpResourceId.NONE) return@transaction DbResourceResponse.errorEmptyId
        val rs = rq.resource
        val result: Boolean =
            ResourceTable.update({ ResourceTable.id eq (id.asString()) }) { toRow(it, rs, randomUuid) } > 0
        DbResourceResponse(rs, result)
    }

    // без проверки на lock
    override suspend fun deleteResource(rq: DbResourceIdRequest): DbResourceResponse = transaction {
        val rs = runBlocking { readResource(rq).data } ?: return@transaction DbResourceResponse.errorEmptyId
        val result: Boolean =
            ResourceTable.deleteWhere { id eq rq.id.asString() } > 0
        DbResourceResponse.success(rs, result)
    }

    override suspend fun searchResource(rq: DbResourceFilterRequest): DbResourcesResponse =
        transaction {
            val result = ResourceTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.otherResourceId != CwpOtherResourceId.NONE) {
                        add(ResourceTable.resourceId eq rq.otherResourceId.asString())
                    }
                    if (rq.scheduleId != CwpScheduleId.NONE) {
                        add(ResourceTable.scheduleId eq rq.scheduleId.asString())
                    }
                    if (rq.ownerId != CwpResourceUserId.NONE) {
                        add(ResourceTable.ownerId eq rq.ownerId.asString())
                    }
                }.reduce { a, b -> a and b }
            }.map(::fromRow)

            DbResourcesResponse.success(result)
        }
}