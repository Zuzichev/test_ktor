package com.crowdproj.resources.biz.worker

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.helper.fail
import com.crowdproj.resources.common.model.CwpResourceError
import com.crowdproj.resources.common.model.CwpResourceState

fun ICorAddExecDsl<CwpResourceContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == CwpResourceState.RUNNING }
    handle {
        fail(
            CwpResourceError(
                code = "validation",
                field = "stub",
                group = "validation",
                title = "validation",
                description = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}