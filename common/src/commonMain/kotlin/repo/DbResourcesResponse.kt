package com.crowdproj.resources.common.repo

import com.crowdproj.resources.common.model.CwpResource
import com.crowdproj.resources.common.model.CwpResourceError

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  21.12.2023 12:30
 */

data class DbResourcesResponse(
    override val data: List<CwpResource>?,
    override val isSuccess: Boolean,
    override val errors: List<CwpResourceError> = emptyList(),
): IDbResponse<List<CwpResource>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbResourcesResponse(null, true)
        fun success(result: List<CwpResource>) = DbResourcesResponse(result, true)
        fun error(errors: List<CwpResourceError>) = DbResourcesResponse(null, false, errors)
        fun error(error: CwpResourceError) = DbResourcesResponse(null, false, listOf(error))
    }
}