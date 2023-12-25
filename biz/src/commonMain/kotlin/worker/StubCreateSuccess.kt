package com.crowdproj.resources.biz.worker

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.*
import com.crowdproj.resources.common.stub.CwpResourceStubs
import com.crowdproj.resources.stubs.CwpResourceStub

fun ICorAddExecDsl<CwpResourceContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == CwpResourceStubs.SUCCESS && state == CwpResourceState.RUNNING }
    handle {
        state = CwpResourceState.FINISHING
        val stub = CwpResourceStub.prepareResult {
            resourceRequest.otherResourceId.takeIf { it != CwpOtherResourceId.NONE }?.also { this.otherResourceId = it }
            resourceRequest.scheduleId.takeIf { it != CwpScheduleId.NONE }?.also { this.scheduleId = it }
        }
        resourceResponse = stub
    }
}