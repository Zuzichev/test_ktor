package com.crowdproj.resources.ktor.controller

import com.crowdproj.resources.api.v1.models.IRequest
import com.crowdproj.resources.api.v1.models.IResponse
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.helper.asCwpResourceError
import com.crowdproj.resources.common.model.CwpResourceCommand
import com.crowdproj.resources.common.model.CwpResourceState
import com.crowdproj.resources.ktor.CwpResourceAppSettings
import com.crowdproj.resources.ktor.base.toModel
import com.crowdproj.resources.logging.common.ICwpLogWrapper
import com.crowdproj.resources.mappers.fromTransport
import com.crowdproj.resources.mappers.log.toLog
import com.crowdproj.resources.mappers.toTransport
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlinx.datetime.Clock

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV1(
    appSettings: CwpResourceAppSettings,
    logger: ICwpLogWrapper,
    logId: String,
    command: CwpResourceCommand? = null,
) {
    val ctx = CwpResourceContext(timeStart = Clock.System.now(),)
    val processor = appSettings.processor

    try {
        logger.doWithLogging(id = logId) {
            ctx.principal = principal<JWTPrincipal>().toModel()
            val request = receive<Q>()
            ctx.fromTransport(request)
            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("${logId}-got")
            )
            processor.exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            respond(ctx.toTransport())
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            command?.also { ctx.command = it }
            logger.error( msg = "$command handling failed",)
            ctx.state = CwpResourceState.FINISHING
            ctx.errors.add(e.asCwpResourceError())
            processor.exec(ctx)
            respond(ctx.toTransport())
        }
    }
}