package com.crowdproj.resources.mappers

import com.crowdproj.resources.api.v1.models.*
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.exception.UnknownCommandException
import com.crowdproj.resources.common.model.*

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  21.12.2023 11:53
 */

// #1
fun CwpResourceContext.toTransport(): IResponse = when (command) {
    CwpResourceCommand.CREATE -> toTransportCreate()
    CwpResourceCommand.READ -> toTransportRead()
    CwpResourceCommand.UPDATE -> toTransportUpdate()
    CwpResourceCommand.DELETE -> toTransportDelete()
    CwpResourceCommand.SEARCH -> toTransportSearch()
    else -> throw UnknownCommandException(command)
}

// #2
fun CwpResourceContext.toTransportCreate() = ResourceCreateResponse(
    requestId = requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == CwpResourceState.FINISHING && errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    resource = resourceResponse.toTransportResource()
)

// #9
fun CwpResourceContext.toTransportRead() = ResourceReadResponse(
    requestId = requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == CwpResourceState.FINISHING && errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    resource = resourceResponse.toTransportResource()
)

// #10
fun CwpResourceContext.toTransportUpdate() = ResourceUpdateResponse(
    requestId = requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == CwpResourceState.FINISHING && errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    resource = resourceResponse.toTransportResource()
)

// #11
fun CwpResourceContext.toTransportDelete() = ResourceDeleteResponse(
    requestId = requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == CwpResourceState.FINISHING && errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    resource = resourceResponse.toTransportResource()
)

// #12
fun CwpResourceContext.toTransportSearch() = ResourceSearchResponse(
    requestId = requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == CwpResourceState.FINISHING && errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    resources = resourcesResponse.toTransportResource()
)

// #13
fun List<CwpResource>.toTransportResource(): List<ResourceResponseObject>? = this
    .map { it.toTransportResource() }
    .toList()
    .takeIf { it.isNotEmpty() }

// #3
fun MutableList<CwpResourceError>.toTransportErrors() = this
    .map { it.toTransportError() }
    .toList()
    .takeIf { it.isNotEmpty() }

// #4
fun CwpResourceError.toTransportError() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
)

// #5
fun CwpResource.toTransportResource() = ResourceResponseObject(
    id = id.takeIf { it != CwpResourceId.NONE }?.asString(),
    scheduleId = scheduleId.takeIf { it != CwpScheduleId.NONE }?.asString(),
    resourceId = otherResourceId.takeIf { it != CwpOtherResourceId.NONE } ?.asString(),
    ownerId = ownerId.takeIf { it != CwpResourceUserId.NONE }?.asString(),
    permissions = permissionsClient.toTransportPermissions()
)

// #7
fun MutableSet<CwpResourcePermission>.toTransportPermissions() = this
    .map { it.toTransportPermissions() }
    .toSet()
    .takeIf { it.isNotEmpty() }

// #8
fun CwpResourcePermission.toTransportPermissions() = when (this) {
    CwpResourcePermission.READ -> ResourcePermissions.READ
    CwpResourcePermission.UPDATE -> ResourcePermissions.UPDATE
    CwpResourcePermission.DELETE -> ResourcePermissions.DELETE
    CwpResourcePermission.MAKE_VISIBLE_PUBLIC -> ResourcePermissions.MAKE_VISIBLE_PUBLIC
    CwpResourcePermission.MAKE_VISIBLE_TO_GROUP -> ResourcePermissions.MAKE_VISIBLE_GROUP
    CwpResourcePermission.MAKE_VISIBLE_TO_OWNER -> ResourcePermissions.MAKE_VISIBLE_OWN
}