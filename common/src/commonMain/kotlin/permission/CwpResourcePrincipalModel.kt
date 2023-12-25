package com.crowdproj.resources.common.permission

import com.crowdproj.resources.common.model.CwpResourceUserId

data class CwpResourcePrincipalModel(
    val id: CwpResourceUserId = CwpResourceUserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<CwpResourceUserGroups> = emptySet()
) {
    companion object {
        val NONE = CwpResourcePrincipalModel()
    }
}