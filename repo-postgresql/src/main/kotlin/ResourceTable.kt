package com.crowdproj.resources.repo.postgresql

import com.crowdproj.resources.common.model.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  24.12.2023 01:57
 */

object ResourceTable : Table(name = "resources") {
    val id = varchar("id", 128)
    val resourceId = text("resource_id")
    val scheduleId = text("schedule_id")
    val ownerId = varchar("owner_id", 128)
    val lock = varchar("lock", 128)

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    fun fromRow(result: InsertStatement<Number>): CwpResource =
        CwpResource(
            id = CwpResourceId(result[id].toString()),
            otherResourceId = CwpOtherResourceId(result[resourceId].toString()),
            scheduleId = CwpScheduleId(result[scheduleId].toString()),
            ownerId = CwpResourceUserId(result[ownerId].toString()),
            lock = CwpResourceLock(result[lock])
        )

    fun fromRow(result: ResultRow): CwpResource =
        CwpResource(
            id = CwpResourceId(result[id].toString()),
            otherResourceId = CwpOtherResourceId(result[resourceId].toString()),
            scheduleId = CwpScheduleId(result[scheduleId].toString()),
            ownerId = CwpResourceUserId(result[ownerId].toString()),
            lock = CwpResourceLock(result[lock])
        )

    fun toRow(it: UpdateBuilder<*>, resource: CwpResource, randomUuid: () -> String) {
        it[id] = resource.id.takeIf { it != CwpResourceId.NONE }?.asString() ?: randomUuid()
        it[resourceId] = resource.otherResourceId.asString()
        it[scheduleId] = resource.scheduleId.asString()
        it[ownerId] = resource.ownerId.asString()
        it[lock] = resource.lock.takeIf { it != CwpResourceLock.NONE }?.asString() ?: randomUuid()
    }
}