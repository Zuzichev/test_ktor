package com.crowdproj.resources.ktor.controller

import com.crowdproj.resources.api.v1.models.*
import com.crowdproj.resources.common.model.CwpResourceCommand
import com.crowdproj.resources.ktor.CwpResourceAppSettings
import com.crowdproj.resources.logging.common.ICwpLogWrapper
import io.ktor.server.application.*

suspend fun ApplicationCall.createResource(appSettings: CwpResourceAppSettings, logger: ICwpLogWrapper) =
    processV1<ResourceCreateRequest, ResourceCreateResponse>(appSettings, logger, "resource-create", CwpResourceCommand.CREATE)

suspend fun ApplicationCall.readResource(appSettings: CwpResourceAppSettings, logger: ICwpLogWrapper) =
    processV1<ResourceReadRequest, ResourceReadResponse>(appSettings, logger, "resource-read", CwpResourceCommand.READ)

suspend fun ApplicationCall.updateResource(appSettings: CwpResourceAppSettings, logger: ICwpLogWrapper) =
    processV1<ResourceUpdateRequest, ResourceUpdateResponse>(appSettings, logger, "resource-update", CwpResourceCommand.UPDATE)


suspend fun ApplicationCall.deleteResource(appSettings: CwpResourceAppSettings, logger: ICwpLogWrapper) =
    processV1<ResourceDeleteRequest, ResourceDeleteResponse>(appSettings, logger, "resource-delete", CwpResourceCommand.DELETE)

suspend fun ApplicationCall.searchResource(appSettings: CwpResourceAppSettings, logger: ICwpLogWrapper) =
    processV1<ResourceSearchRequest, ResourceSearchResponse>(appSettings, logger, "resource-search", CwpResourceCommand.SEARCH)