package com.crowdproj.resources.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.helper.errorValidation
import com.crowdproj.resources.common.helper.fail
import com.crowdproj.resources.common.model.CwpOtherResourceId

fun ICorAddExecDsl<CwpResourceContext>.validateOtherResourceIdProperFormat(title: String) = worker {
    this.title = title

    val regExp = Regex("^[0-9]+$")
    on { resourceValidating.otherResourceId != CwpOtherResourceId.NONE && !resourceValidating.otherResourceId.asString().matches(regExp) }
    handle {
        fail(
            errorValidation(
                field = "otherResourceId",
                violationCode = "badFormat",
                description = "value must contain only numbers"
            )
        )
    }
}