package com.crowdproj.resources.biz.worker

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceError
import com.crowdproj.resources.common.model.CwpResourceState
import com.crowdproj.resources.common.stub.CwpResourceStubs

fun ICorAddExecDsl<CwpResourceContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == CwpResourceStubs.NOT_FOUND && state == CwpResourceState.RUNNING }
    handle {
        state = CwpResourceState.FINISHING
        this.errors.add(
            CwpResourceError(
                group = "internal",
                code = "internal-db",
                title = "internal-db",
                description = "Internal error"
            )
        )
    }
}