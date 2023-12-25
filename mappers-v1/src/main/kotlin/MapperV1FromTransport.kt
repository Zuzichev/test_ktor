package com.crowdproj.resources.mappers

import com.crowdproj.resources.api.v1.models.*
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.exception.UnknownRequestException
import com.crowdproj.resources.common.model.*
import com.crowdproj.resources.common.stub.CwpResourceStubs

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  22.12.2023 11:49
 */

// #1
fun CwpResourceContext.fromTransport(request: IRequest) = when (request) {
    is ResourceCreateRequest -> fromTransport(request)
    is ResourceReadRequest -> fromTransport(request)
    is ResourceUpdateRequest -> fromTransport(request)
    is ResourceDeleteRequest -> fromTransport(request)
    is ResourceSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestException(request::class)
}

// #3
fun IRequest?.requestId() = this?.requestId?.let { CwpResourceRequestId(it) } ?: CwpResourceRequestId.NONE

// #5
fun String?.toResourceId() = this?.let { CwpResourceId(it) } ?: CwpResourceId.NONE
fun String?.toOtherResourcesId() = this?.let { CwpOtherResourceId(it) } ?: CwpOtherResourceId.NONE
fun String?.toScheduleId() = this?.let { CwpScheduleId(it) } ?: CwpScheduleId.NONE

// #9
fun String?.toResourceWithId() = CwpResource(id = this.toResourceId())

// #6
fun ResourceDebug?.transportToWorkMode() = when (this?.mode) {
    ResourceRequestDebugMode.PROD -> CwpResourceWorkMode.PROD
    ResourceRequestDebugMode.STUB -> CwpResourceWorkMode.STUB
    ResourceRequestDebugMode.TEST -> CwpResourceWorkMode.TEST
    null -> CwpResourceWorkMode.PROD
}

// #7
fun ResourceDebug?.transportToStubCase() = when (this?.stub) {
    ResourceRequestDebugStubs.SUCCESS -> CwpResourceStubs.SUCCESS
    ResourceRequestDebugStubs.NOT_FOUND -> CwpResourceStubs.NOT_FOUND
    ResourceRequestDebugStubs.BAD_ID -> CwpResourceStubs.BAD_ID
    null -> CwpResourceStubs.NONE
}

// #4
fun ResourceCreateObject.toInternal() = CwpResource(
    otherResourceId = this.resourceId.toOtherResourcesId(), // -> #5
    scheduleId = this.scheduleId.toScheduleId(), // -> #5
)

// #14
fun ResourceUpdateObject.toInternal() = CwpResource(
    id = this.id.toResourceId(),
    otherResourceId = this.resourceId.toOtherResourcesId(), // -> #5
    scheduleId = this.scheduleId.toScheduleId(), // -> #5
)

// #15
fun ResourceSearchFilter?.toInternal() = CwpResourceFilter(
    otherResourceId = this?.resourceId.toOtherResourcesId(), // -> #5
    scheduleId = this?.scheduleId.toScheduleId(), // -> #5
)

// #2
fun CwpResourceContext.fromTransport(request: ResourceCreateRequest) {
    command = CwpResourceCommand.CREATE
    state = CwpResourceState.NONE
    requestId = request.requestId() // -> #3
    resourceRequest = request.resource?.toInternal() ?: CwpResource() // -> #4
    workMode = request.debug.transportToWorkMode() // -> #6
    stubCase = request.debug.transportToStubCase() // -> #7
}

// #11
fun CwpResourceContext.fromTransport(request: ResourceReadRequest) {
    command = CwpResourceCommand.READ
    state = CwpResourceState.NONE
    requestId = request.requestId()
    resourceRequest = request.resource?.id.toResourceWithId() // -> #9
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

// #12
fun CwpResourceContext.fromTransport(request: ResourceUpdateRequest) {
    command = CwpResourceCommand.UPDATE
    state = CwpResourceState.NONE
    requestId = request.requestId()
    resourceRequest = request.resource?.toInternal() ?: CwpResource()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

// #8
fun CwpResourceContext.fromTransport(request: ResourceDeleteRequest) {
    command = CwpResourceCommand.DELETE
    state = CwpResourceState.NONE
    requestId = request.requestId()
    resourceRequest = request.resource?.id.toResourceWithId() // -> #9 (когда объект приходит не как объект, а как id)
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

// #14
fun CwpResourceContext.fromTransport(request: ResourceSearchRequest) {
    command = CwpResourceCommand.SEARCH
    state = CwpResourceState.NONE
    requestId = request.requestId()
    resourceFilterRequest = request.resourceFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}