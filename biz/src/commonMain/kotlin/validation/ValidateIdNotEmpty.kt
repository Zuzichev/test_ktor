package com.crowdproj.resources.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.helper.errorValidation
import com.crowdproj.resources.common.helper.fail

fun ICorAddExecDsl<CwpResourceContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { resourceValidating.id.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}