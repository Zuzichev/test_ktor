package com.crowdproj.resources.biz.repo

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState

fun ICorAddExecDsl<CwpResourceContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные из БД и данные от пользователя."
    on { state == CwpResourceState.RUNNING }
    handle {
        resourceRepoPrepare = resourceRepoRead.deepCopy().apply {
            this.otherResourceId = resourceValidated.otherResourceId
            scheduleId = resourceValidated.scheduleId
        }
    }
}