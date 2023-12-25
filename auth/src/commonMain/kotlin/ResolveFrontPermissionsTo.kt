package com.crowdproj.resources.auth

import com.crowdproj.resources.common.model.CwpResourcePermission
import com.crowdproj.resources.common.permission.CwpResourcePrincipalRelations
import com.crowdproj.resources.common.permission.CwpResourceUserPermissions

fun resolveFrontPermissions(
    permissions: Iterable<CwpResourceUserPermissions>,
    relations: Iterable<CwpResourcePrincipalRelations>,
) = mutableSetOf<CwpResourcePermission>()
    .apply {
        for (permission in permissions) {
            for (relation in relations) {
                accessTable[permission]?.get(relation)?.let { this@apply.add(it) }
            }
        }
    }
    .toSet()

private val accessTable = mapOf(
    // READ
    CwpResourceUserPermissions.READ_OWN to mapOf(
        CwpResourcePrincipalRelations.OWN to CwpResourcePermission.READ
    ),
    CwpResourceUserPermissions.READ_GROUP to mapOf(
        CwpResourcePrincipalRelations.GROUP to CwpResourcePermission.READ
    ),
    CwpResourceUserPermissions.READ_PUBLIC to mapOf(
        CwpResourcePrincipalRelations.PUBLIC to CwpResourcePermission.READ
    ),
    CwpResourceUserPermissions.READ_CANDIDATE to mapOf(
        CwpResourcePrincipalRelations.MODERATABLE to CwpResourcePermission.READ
    ),

    // UPDATE
    CwpResourceUserPermissions.UPDATE_OWN to mapOf(
        CwpResourcePrincipalRelations.OWN to CwpResourcePermission.UPDATE
    ),
    CwpResourceUserPermissions.UPDATE_PUBLIC to mapOf(
        CwpResourcePrincipalRelations.MODERATABLE to CwpResourcePermission.UPDATE
    ),
    CwpResourceUserPermissions.UPDATE_CANDIDATE to mapOf(
        CwpResourcePrincipalRelations.MODERATABLE to CwpResourcePermission.UPDATE
    ),

    // DELETE
    CwpResourceUserPermissions.DELETE_OWN to mapOf(
        CwpResourcePrincipalRelations.OWN to CwpResourcePermission.DELETE
    ),
    CwpResourceUserPermissions.DELETE_PUBLIC to mapOf(
        CwpResourcePrincipalRelations.MODERATABLE to CwpResourcePermission.DELETE
    ),
    CwpResourceUserPermissions.DELETE_CANDIDATE to mapOf(
        CwpResourcePrincipalRelations.MODERATABLE to CwpResourcePermission.DELETE
    ),
)