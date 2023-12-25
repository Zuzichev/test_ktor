package com.crowdproj.resources.biz.validation

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.helper.errorValidation
import com.crowdproj.resources.common.helper.fail
import com.crowdproj.resources.common.model.CwpResourceId

fun ICorAddExecDsl<CwpResourceContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { resourceValidating.id != CwpResourceId.NONE && !resourceValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = resourceValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}