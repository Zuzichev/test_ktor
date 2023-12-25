package com.crowdproj.resources.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState
import com.crowdproj.resources.common.repo.DbResourceIdRequest

fun ICorAddExecDsl<CwpResourceContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == CwpResourceState.RUNNING }
    handle {
        val request = DbResourceIdRequest(resourceRepoPrepare)
        val result = resourceRepo.deleteResource(request)
        if (!result.isSuccess) {
            state = CwpResourceState.FINISHING
            errors.addAll(result.errors)
        }
        resourceRepoDone = resourceRepoRead
    }
}