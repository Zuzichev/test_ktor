package com.crowdproj.resources.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState

fun ICorAddExecDsl<CwpResourceContext>.finishRatingValidation(title: String) = worker {
    this.title = title
    on { state == CwpResourceState.RUNNING }
    handle {
        resourceValidated = resourceValidating
    }
}

fun ICorAddExecDsl<CwpResourceContext>.finishRatingFilterValidation(title: String) = worker {
    this.title = title
    on { state == CwpResourceState.RUNNING }
    handle {
        resourceFilterValidated = resourceFilterValidating
    }
}