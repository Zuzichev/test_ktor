package com.crowdproj.resources.biz.permission

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.auth.checkPermitted
import com.crowdproj.resources.auth.resolveRelationsTo
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.helper.fail
import com.crowdproj.resources.common.model.CwpResourceError
import com.crowdproj.resources.common.model.CwpResourceState

fun ICorAddExecDsl<CwpResourceContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа."
    on { state == CwpResourceState.RUNNING }
    worker("Вычисление отношения к принципалу.") {
        resourceRepoRead.principalRelations = resourceRepoRead.resolveRelationsTo(principal)
    }
    worker("Определение доступа.") {
        permitted = checkPermitted(command, resourceRepoRead.principalRelations, permissionsChain)
    }
    worker {
        this.title = "Валидация прав доступа."
        description = "Проверка наличия прав для выполнения операции."
        on { !permitted }
        handle {
            fail(CwpResourceError(title = "Пользователю не разрешено выполнять эту операцию."))
        }
    }
}