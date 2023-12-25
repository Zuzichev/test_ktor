package com.crowdproj.resources.common.repo

import com.crowdproj.resources.common.model.CwpResourceError

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  21.12.2023 12:14
 */

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<CwpResourceError>
}