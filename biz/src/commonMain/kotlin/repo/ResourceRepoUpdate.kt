package com.crowdproj.resources.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState
import com.crowdproj.resources.common.repo.DbResourceRequest

fun ICorAddExecDsl<CwpResourceContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == CwpResourceState.RUNNING }
    handle {
        val request = DbResourceRequest(resourceRepoPrepare)
        val result = resourceRepo.updateResource(request)
        val resultRating = result.data
        if (result.isSuccess && resultRating != null) {
            resourceRepoDone = resultRating
        } else {
            state = CwpResourceState.FINISHING
            errors.addAll(result.errors)
            resourceRepoDone
        }
    }
}