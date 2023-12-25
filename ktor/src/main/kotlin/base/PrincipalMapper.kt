package com.crowdproj.resources.ktor.base

import com.crowdproj.resources.common.model.CwpResourceUserId
import com.crowdproj.resources.common.permission.CwpResourcePrincipalModel
import com.crowdproj.resources.common.permission.CwpResourceUserGroups
import com.crowdproj.resources.ktor.base.KtorAuthConfig.Companion.F_NAME_CLAIM
import com.crowdproj.resources.ktor.base.KtorAuthConfig.Companion.GROUPS_CLAIM
import com.crowdproj.resources.ktor.base.KtorAuthConfig.Companion.ID_CLAIM
import com.crowdproj.resources.ktor.base.KtorAuthConfig.Companion.L_NAME_CLAIM
import com.crowdproj.resources.ktor.base.KtorAuthConfig.Companion.M_NAME_CLAIM
import io.ktor.server.auth.jwt.*

fun JWTPrincipal?.toModel() = this?.run {
    CwpResourcePrincipalModel(
        id = payload.getClaim(ID_CLAIM).asString()?.let { CwpResourceUserId(it) } ?: CwpResourceUserId.NONE,
        fname = payload.getClaim(F_NAME_CLAIM).asString() ?: "",
        mname = payload.getClaim(M_NAME_CLAIM).asString() ?: "",
        lname = payload.getClaim(L_NAME_CLAIM).asString() ?: "",
        groups = payload
            .getClaim(GROUPS_CLAIM)
            ?.asList(String::class.java)
            ?.mapNotNull {
                when(it) {
                    "USER" -> CwpResourceUserGroups.USER
                    else -> null
                }
            }?.toSet() ?: emptySet()
    )
} ?: CwpResourcePrincipalModel.NONE