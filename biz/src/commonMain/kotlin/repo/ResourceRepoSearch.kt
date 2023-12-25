package com.crowdproj.resources.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState
import com.crowdproj.resources.common.repo.DbResourceFilterRequest

fun ICorAddExecDsl<CwpResourceContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == CwpResourceState.RUNNING }
    handle {
        val request = DbResourceFilterRequest(
            otherResourceId = resourceFilterValidated.otherResourceId,
            scheduleId = resourceFilterValidated.scheduleId,
            ownerId = resourceFilterValidated.ownerId,
        )
        val result = resourceRepo.searchResource(request)
        val resultRs = result.data
        if (result.isSuccess && resultRs != null) {
            resourcesRepoDone = resultRs.toMutableList()
        } else {
            state = CwpResourceState.FINISHING
            errors.addAll(result.errors)
        }
    }
}