package com.crowdproj.resources.api

import com.crowdproj.resources.api.v1.models.IRequest
import com.crowdproj.resources.api.v1.models.IResponse
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  20.12.2023 13:47
 */

val apiV1Mapper = ObjectMapper()

fun apiV1RequestSerialize(request: IRequest): String = apiV1Mapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequest> apiV1RequestDeserialize(json: String): T =
    apiV1Mapper.readValue(json, IRequest::class.java) as T

fun apiV1ResponseSerialize(response: IResponse): String = apiV1Mapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T : IResponse> apiV1ResponseDeserialize(json: String): T =
    apiV1Mapper.readValue(json, IResponse::class.java) as T