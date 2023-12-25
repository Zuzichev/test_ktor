package com.crowdproj.resources.common.model

import com.crowdproj.resources.common.permission.CwpResourcePrincipalRelations

data class CwpResource(
    var id: CwpResourceId = CwpResourceId.NONE,
    var otherResourceId: CwpOtherResourceId = CwpOtherResourceId.NONE,
    var scheduleId: CwpScheduleId = CwpScheduleId.NONE,
    var ownerId: CwpResourceUserId = CwpResourceUserId.NONE,
    var deleted: Boolean = false,
    var visibility: CwpResourceVisibility = CwpResourceVisibility.NONE,
    var lock: CwpResourceLock = CwpResourceLock.NONE,
    var principalRelations: Set<CwpResourcePrincipalRelations> = emptySet(),
    val permissionsClient: MutableSet<CwpResourcePermission> = mutableSetOf()
) {
    fun isEmpty() = this == NONE
    fun deepCopy(): CwpResource = copy(
        principalRelations = principalRelations.toSet(),
        permissionsClient = permissionsClient.toMutableSet()
    )

    companion object {
        private val NONE = CwpResource()
    }

}