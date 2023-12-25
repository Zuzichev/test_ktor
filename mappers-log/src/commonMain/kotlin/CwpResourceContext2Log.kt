package com.crowdproj.resources.mappers.log

import com.crowdproj.resources.api.log.models.*
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.*
import kotlinx.datetime.Clock

fun CwpResourceContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "cwp-resources",
    resource = toCwpLog(),
    errors = errors.map { it.toLog() },
)

fun CwpResourceContext.toCwpLog(): CwpLogModel? {
    val ratingNone = CwpResource()
    return CwpLogModel(
        requestId = requestId.takeIf { it != CwpResourceRequestId.NONE }?.asString(),
        requestResource = resourceRequest.takeIf { it != ratingNone }?.toLog(),
        responseResource = resourceResponse.takeIf { it != ratingNone }?.toLog(),
        responseResources = resourcesResponse.takeIf { it.isNotEmpty() }?.filter { it != ratingNone }?.map { it.toLog() },
        requestFilter = resourceFilterRequest.takeIf { it != CwpResourceFilter() }?.toLog(),
    ).takeIf { it != CwpLogModel() }
}

private fun CwpResourceFilter.toLog() = ResourceFilterLog(
    searchString = otherResourceId.takeIf { it != CwpOtherResourceId.NONE }?.asString(),
    ownerId = ownerId.takeIf { it != CwpResourceUserId.NONE }?.asString(),
)

fun CwpResourceError.toLog() = ErrorLogModel(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
)

fun CwpResource.toLog() = ResourceLog(
    id = id.takeIf { it != CwpResourceId.NONE }?.asString(),
    resourceId = otherResourceId.takeIf { it != CwpOtherResourceId.NONE }?.asString(),
    scheduleId = scheduleId.takeIf { it != CwpScheduleId.NONE }?.asString(),
    ownerId = ownerId.takeIf { it != CwpResourceUserId.NONE }?.asString(),
    permissions = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)