package com.crowdproj.resources.ktor.plugin

import com.crowdproj.resources.ktor.CwpResourceAppSettings
import com.crowdproj.resources.logging.logback.CwpLogWrapperLogback
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.doublereceive.*
import org.slf4j.event.Level

fun Application.configureCallLogging(appSettings: CwpResourceAppSettings, clazz: String) {

    install(DoubleReceive)

    install(CallLogging) {
        level = Level.INFO
        val lgr = appSettings
            .corSettings
            .loggerProvider
            .logger(clazz) as? CwpLogWrapperLogback
        lgr?.logger?.also { logger = it }
    }
}