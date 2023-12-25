package com.crowdproj.resources.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState

fun ICorAddExecDsl<CwpResourceContext>.validation(block: ICorAddExecDsl<CwpResourceContext>.() -> Unit) = chain {
    block()
    title = "Валидация"
    on { state == CwpResourceState.RUNNING }
}