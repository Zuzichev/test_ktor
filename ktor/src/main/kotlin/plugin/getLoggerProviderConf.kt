package com.crowdproj.resources.ktor.plugin

import com.crowdproj.resources.logging.common.CwpLoggerProvider
import com.crowdproj.resources.logging.logback.resourceLoggerLogback
import io.ktor.server.application.*

fun Application.getLoggerProviderConf(): CwpLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback", null -> CwpLoggerProvider { resourceLoggerLogback(it) }
        else -> throw Exception("Logger $mode is not allowed.")
    }