package com.crowdproj.resources.biz.general

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState

fun ICorAddExecDsl<CwpResourceContext>.initStatus(title: String) = worker {
    this.title = title
    on { state == CwpResourceState.NONE }
    handle { state = CwpResourceState.RUNNING }
}