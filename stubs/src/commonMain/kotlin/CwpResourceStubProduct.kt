package com.crowdproj.resources.stubs

import com.crowdproj.resources.common.model.*
import kotlinx.datetime.Instant

object CwpResourceStubProduct {
    val RESOURCE_PRODUCT: CwpResource
        get() = CwpResource(
            id = CwpResourceId("111"),
            otherResourceId = CwpOtherResourceId("222"),
            scheduleId = CwpScheduleId("333"),
            ownerId = CwpResourceUserId("1"),
            permissionsClient = mutableSetOf(
                CwpResourcePermission.READ,
                CwpResourcePermission.UPDATE,
                CwpResourcePermission.DELETE,
                CwpResourcePermission.MAKE_VISIBLE_PUBLIC,
                CwpResourcePermission.MAKE_VISIBLE_TO_GROUP,
                CwpResourcePermission.MAKE_VISIBLE_TO_OWNER,
            )
        )
}