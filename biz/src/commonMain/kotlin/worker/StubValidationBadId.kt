package com.crowdproj.resources.biz.worker

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceError
import com.crowdproj.resources.common.model.CwpResourceState
import com.crowdproj.resources.common.stub.CwpResourceStubs

fun ICorAddExecDsl<CwpResourceContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    on { stubCase == CwpResourceStubs.BAD_ID && state == CwpResourceState.RUNNING }
    handle {
        state = CwpResourceState.FINISHING
        this.errors.add(
            CwpResourceError(
                group = "validation",
                code = "validation-id",
                field = "id",
                title = "validation-id",
                description = "Wrong id field"
            )
        )
    }
}