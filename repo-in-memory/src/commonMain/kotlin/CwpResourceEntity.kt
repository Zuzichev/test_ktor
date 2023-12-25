package com.crowdproj.resources.repo.inmemory

import com.crowdproj.resources.common.model.*

data class CwpResourceEntity(
    val id: String? = null,
    val otherResourceId: String? = null,
    val scheduleId: String? = null,
    val ownerId: String? = null,
    val lock: String? = null,
) {
    constructor(model: CwpResource) : this(
        id = model.id.asString().takeIf { it != CwpResourceId.NONE.asString() },
        otherResourceId = model.otherResourceId.asString().takeIf { it != CwpOtherResourceId.NONE.asString() },
        scheduleId = model.scheduleId.asString().takeIf { it != CwpScheduleId.NONE.asString() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = CwpResource(
        id = id?.let { CwpResourceId(it) } ?: CwpResourceId.NONE,
        otherResourceId = otherResourceId?.let { CwpOtherResourceId(it) } ?: CwpOtherResourceId.NONE,
        scheduleId = scheduleId?.let { CwpScheduleId(it) } ?: CwpScheduleId.NONE,
        ownerId = ownerId?.let { CwpResourceUserId(it) } ?: CwpResourceUserId.NONE,
        lock = lock?.let { CwpResourceLock(it) } ?: CwpResourceLock.NONE,
    )
}