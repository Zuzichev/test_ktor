package com.crowdproj.resources.biz.general

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceCommand
import com.crowdproj.resources.common.model.CwpResourceState

fun ICorAddExecDsl<CwpResourceContext>.operation(
    title: String,
    command: CwpResourceCommand,
    block: ICorAddExecDsl<CwpResourceContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == CwpResourceState.RUNNING }
}