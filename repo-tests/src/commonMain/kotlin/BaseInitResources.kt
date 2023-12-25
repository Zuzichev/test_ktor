package com.crowdproj.resources.repo.tests

import com.crowdproj.resources.common.model.*

abstract class BaseInitResources(val op: String): IInitObjects<CwpResource> {

    open val lockOld: CwpResourceLock = CwpResourceLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: CwpResourceLock = CwpResourceLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        otherResourceId: CwpOtherResourceId = CwpOtherResourceId("1"),
        scheduleId: CwpScheduleId = CwpScheduleId("11"),
        ownerId: CwpResourceUserId = CwpResourceUserId("owner-123"),
        lock: CwpResourceLock = lockOld,
    ) = CwpResource(
        id = CwpResourceId("resource-repo-$op-$suf"),
        otherResourceId = otherResourceId,
        scheduleId = scheduleId,
        ownerId = ownerId,
        lock = lock,
    )
}