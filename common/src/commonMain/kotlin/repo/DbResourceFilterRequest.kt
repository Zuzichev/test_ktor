package com.crowdproj.resources.common.repo

import com.crowdproj.resources.common.model.*

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  21.12.2023 12:28
 */

data class DbResourceFilterRequest(
    val otherResourceId: CwpOtherResourceId = CwpOtherResourceId.NONE,
    val scheduleId: CwpScheduleId = CwpScheduleId.NONE,
    val ownerId: CwpResourceUserId = CwpResourceUserId.NONE,
)