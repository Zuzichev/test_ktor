package com.crowdproj.resources.auth

import com.crowdproj.resources.common.permission.CwpResourceUserGroups
import com.crowdproj.resources.common.permission.CwpResourceUserPermissions

fun resolveChainPermissions(groups: Iterable<CwpResourceUserGroups>) =
    mutableSetOf<CwpResourceUserPermissions>()
        .apply {
            addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
            removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
        }
        .toSet()

private val groupPermissionsAdmits = mapOf(
    CwpResourceUserGroups.USER to setOf(
        CwpResourceUserPermissions.READ_OWN,
        CwpResourceUserPermissions.READ_PUBLIC,
        CwpResourceUserPermissions.CREATE_OWN,
        CwpResourceUserPermissions.UPDATE_OWN,
        CwpResourceUserPermissions.DELETE_OWN,
    ),
    CwpResourceUserGroups.MODERATOR_CWP to setOf(),
    CwpResourceUserGroups.ADMIN_RATINGS to setOf(),
    CwpResourceUserGroups.TEST to setOf(),
    CwpResourceUserGroups.BAN_RATINGS to setOf(),
)

private val groupPermissionsDenys = mapOf(
    CwpResourceUserGroups.USER to setOf(),
    CwpResourceUserGroups.MODERATOR_CWP to setOf(),
    CwpResourceUserGroups.ADMIN_RATINGS to setOf(),
    CwpResourceUserGroups.TEST to setOf(),
    CwpResourceUserGroups.BAN_RATINGS to setOf(
        CwpResourceUserPermissions.UPDATE_OWN,
        CwpResourceUserPermissions.CREATE_OWN,
    ),
)