package com.crowdproj.resources.auth

import com.crowdproj.resources.common.model.CwpResourceCommand
import com.crowdproj.resources.common.permission.CwpResourcePrincipalRelations
import com.crowdproj.resources.common.permission.CwpResourceUserPermissions

fun checkPermitted(
    command: CwpResourceCommand,
    relations: Iterable<CwpResourcePrincipalRelations>,
    permissions: Iterable<CwpResourceUserPermissions>,
) =
    relations.asSequence().flatMap { relation ->
        permissions.map { permission ->
            AccessTableConditions(
                command = command,
                permission = permission,
                relation = relation,
            )
        }
    }.any {
        accessTable[it] != null
    }

private data class AccessTableConditions(
    val command: CwpResourceCommand,
    val permission: CwpResourceUserPermissions,
    val relation: CwpResourcePrincipalRelations,
)

private val accessTable = mapOf(
    // Create
    AccessTableConditions(
        command = CwpResourceCommand.CREATE,
        permission = CwpResourceUserPermissions.CREATE_OWN,
        relation = CwpResourcePrincipalRelations.NEW,
    ) to true,

    // Read
    AccessTableConditions(
        command = CwpResourceCommand.READ,
        permission = CwpResourceUserPermissions.READ_OWN,
        relation = CwpResourcePrincipalRelations.OWN,
    ) to true,

    AccessTableConditions(
        command = CwpResourceCommand.READ,
        permission = CwpResourceUserPermissions.READ_PUBLIC,
        relation = CwpResourcePrincipalRelations.PUBLIC,
    ) to true,

    // Update
    AccessTableConditions(
        command = CwpResourceCommand.UPDATE,
        permission = CwpResourceUserPermissions.UPDATE_OWN,
        relation = CwpResourcePrincipalRelations.OWN,
    ) to true,

    // Delete
    AccessTableConditions(
        command = CwpResourceCommand.DELETE,
        permission = CwpResourceUserPermissions.DELETE_OWN,
        relation = CwpResourcePrincipalRelations.OWN,
    ) to true,
)