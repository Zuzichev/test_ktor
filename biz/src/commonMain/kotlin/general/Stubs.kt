package com.crowdproj.resources.biz.general

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState
import com.crowdproj.resources.common.model.CwpResourceWorkMode

fun ICorAddExecDsl<CwpResourceContext>.stubs(
    title: String,
    block: ICorAddExecDsl<CwpResourceContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { workMode == CwpResourceWorkMode.STUB && state == CwpResourceState.RUNNING }
}