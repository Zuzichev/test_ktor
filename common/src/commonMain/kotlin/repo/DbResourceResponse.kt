package com.crowdproj.resources.common.repo

import com.crowdproj.resources.common.model.CwpResource
import com.crowdproj.resources.common.model.CwpResourceError
import com.crowdproj.resources.common.helper.errorNotFound as cwpErrorNotFound
import com.crowdproj.resources.common.helper.errorSave as cwpErrorSave
import com.crowdproj.resources.common.helper.errorEmptyId as cwpErrorEmptyId

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  21.12.2023 12:13
 */

data class DbResourceResponse(
    override val data: CwpResource?,
    override val isSuccess: Boolean,
    override val errors: List<CwpResourceError> = emptyList(),
): IDbResponse<CwpResource> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbResourceResponse(null, true)
        fun success(result: CwpResource, isSuccess: Boolean = true) = DbResourceResponse(result, isSuccess)
        fun error(errors: List<CwpResourceError>) = DbResourceResponse(null, false, errors)
        fun error(error: CwpResourceError) = DbResourceResponse(null, false, listOf(error))

        val errorNotFound = error(cwpErrorNotFound)
        val errorSave = error(cwpErrorSave)
        val errorEmptyId = error(cwpErrorEmptyId)
    }
}