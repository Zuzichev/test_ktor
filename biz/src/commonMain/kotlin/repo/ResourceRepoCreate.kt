package com.crowdproj.resources.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState
import com.crowdproj.resources.common.repo.DbResourceRequest

fun ICorAddExecDsl<CwpResourceContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление объявления в БД"
    on { state == CwpResourceState.RUNNING }
    handle {
        val request = DbResourceRequest(resourceRepoPrepare)
        val result = resourceRepo.createResource(request)
        val resultRs = result.data
        if (result.isSuccess && resultRs != null) {
            resourceRepoDone = resultRs
        } else {
            state = CwpResourceState.FINISHING
            errors.addAll(result.errors)
        }
    }
}