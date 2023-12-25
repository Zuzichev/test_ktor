package com.crowdproj.resources.common.repo

import com.crowdproj.resources.common.model.CwpResource
import com.crowdproj.resources.common.model.CwpResourceId
import com.crowdproj.resources.common.model.CwpResourceLock

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  21.12.2023 12:22
 */

data class DbResourceIdRequest(
    val id: CwpResourceId,
    val lock: CwpResourceLock = CwpResourceLock.NONE,
) {
    constructor(resource: CwpResource) : this(resource.id, resource.lock)
}