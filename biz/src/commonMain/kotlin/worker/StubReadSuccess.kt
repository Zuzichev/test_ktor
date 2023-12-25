package com.crowdproj.resources.biz.worker

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceId
import com.crowdproj.resources.common.model.CwpResourceState
import com.crowdproj.resources.common.stub.CwpResourceStubs
import com.crowdproj.resources.stubs.CwpResourceStub

fun ICorAddExecDsl<CwpResourceContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    on { stubCase == CwpResourceStubs.SUCCESS && state == CwpResourceState.RUNNING }
    handle {
        state = CwpResourceState.FINISHING
        val stub = CwpResourceStub.prepareResult {
            resourceRequest.id.takeIf { it != CwpResourceId.NONE }?.also { this.id = it }
        }
        resourceResponse = stub
    }
}