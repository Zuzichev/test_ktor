package com.crowdproj.resources.common

import com.crowdproj.resources.common.model.*
import com.crowdproj.resources.common.permission.CwpResourcePrincipalModel
import com.crowdproj.resources.common.permission.CwpResourceUserPermissions
import com.crowdproj.resources.common.repo.IResourceRepository
import com.crowdproj.resources.common.stub.CwpResourceStubs
import kotlinx.datetime.Instant

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  20.12.2023 15:36
 */

data class CwpResourceContext(
    var command: CwpResourceCommand = CwpResourceCommand.NONE,
    var state: CwpResourceState = CwpResourceState.NONE,
    var errors: MutableList<CwpResourceError> = mutableListOf(),
    var settings: CwpResourceCorSettings = CwpResourceCorSettings.NONE,

    var workMode: CwpResourceWorkMode = CwpResourceWorkMode.STUB,
    var stubCase: CwpResourceStubs = CwpResourceStubs.NONE,

    var principal: CwpResourcePrincipalModel = CwpResourcePrincipalModel.NONE,
    val permissionsChain: MutableSet<CwpResourceUserPermissions> = mutableSetOf(),
    var permitted: Boolean = false,

    var requestId: CwpResourceRequestId = CwpResourceRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var resourceRepo: IResourceRepository = IResourceRepository.NONE,
    var resourceRepoRead: CwpResource = CwpResource(),
    var resourceRepoPrepare: CwpResource = CwpResource(),
    var resourceRepoDone: CwpResource = CwpResource(),
    var resourcesRepoDone: MutableList<CwpResource> = mutableListOf(),

    var resourceRequest: CwpResource = CwpResource(),
    var resourceFilterRequest: CwpResourceFilter = CwpResourceFilter(),

    var resourceResponse: CwpResource = CwpResource(),
    var resourcesResponse: MutableList<CwpResource> = mutableListOf(),

    var resourceValidating: CwpResource = CwpResource(),
    var resourceFilterValidating: CwpResourceFilter = CwpResourceFilter(),

    var resourceValidated: CwpResource = CwpResource(),
    var resourceFilterValidated: CwpResourceFilter = CwpResourceFilter(),
)