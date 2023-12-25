package com.crowdproj.resources.biz.general

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState
import com.crowdproj.resources.common.model.CwpResourceWorkMode

fun ICorAddExecDsl<CwpResourceContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос."
    on { workMode != CwpResourceWorkMode.STUB }
    handle {
        resourceResponse = resourceRepoDone
        resourcesResponse = resourcesRepoDone
        state = when (val st = state) {
            CwpResourceState.RUNNING -> CwpResourceState.FINISHING
            else -> st
        }
    }
}