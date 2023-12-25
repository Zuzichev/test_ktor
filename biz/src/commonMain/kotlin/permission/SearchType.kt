package com.crowdproj.resources.biz.permission

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceSearchPermissions
import com.crowdproj.resources.common.model.CwpResourceState
import com.crowdproj.resources.common.permission.CwpResourceUserPermissions

fun ICorAddExecDsl<CwpResourceContext>.searchTypes(title: String) = chain {
    this.title = title
    description = "Добавление ограничений в поисковый запрос согласно правам доступа и др. политикам"
    on { state == CwpResourceState.RUNNING }
    worker("Определение типа поиска") {
        resourceFilterValidated.searchPermissions = setOfNotNull(
            CwpResourceSearchPermissions.OWN.takeIf { permissionsChain.contains(CwpResourceUserPermissions.SEARCH_OWN) },
            CwpResourceSearchPermissions.PUBLIC.takeIf { permissionsChain.contains(CwpResourceUserPermissions.SEARCH_PUBLIC) },
            CwpResourceSearchPermissions.REGISTERED.takeIf { permissionsChain.contains(CwpResourceUserPermissions.SEARCH_REGISTERED) },
        ).toMutableSet()
    }
}