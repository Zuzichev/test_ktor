package com.crowdproj.resources.common.model

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.03.2023 14:17
 */

data class CwpResourceFilter(
    val otherResourceId: CwpOtherResourceId = CwpOtherResourceId.NONE,
    val scheduleId: CwpScheduleId = CwpScheduleId.NONE,
    val ownerId: CwpResourceUserId = CwpResourceUserId.NONE,
    var searchPermissions: MutableSet<CwpResourceSearchPermissions> = mutableSetOf(),
)