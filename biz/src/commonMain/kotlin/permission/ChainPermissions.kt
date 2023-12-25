package com.crowdproj.resources.biz.permission

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.auth.resolveChainPermissions
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState

fun ICorAddExecDsl<CwpResourceContext>.chainPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление прав доступа для групп пользователей."

    on { state == CwpResourceState.RUNNING }

    handle {
        permissionsChain.addAll(resolveChainPermissions(principal.groups))
        println("PRINCIPAL: $principal")
        println("PERMISSIONS: $permissionsChain")
    }
}