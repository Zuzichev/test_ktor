package com.crowdproj.resources.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState
import com.crowdproj.resources.common.repo.DbResourceIdRequest

fun ICorAddExecDsl<CwpResourceContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение рейтинга из БД"
    on { state == CwpResourceState.RUNNING }
    handle {
        val request = DbResourceIdRequest(resourceValidated)
        val result = resourceRepo.readResource(request)
        val resultRs = result.data
        if (result.isSuccess && resultRs != null) {
            resourceRepoRead = resultRs
        } else {
            state = CwpResourceState.FINISHING
            errors.addAll(result.errors)
        }
    }
}