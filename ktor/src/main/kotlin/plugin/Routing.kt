package com.crowdproj.resources.ktor.plugin

import com.crowdproj.resources.ktor.CwpResourceAppSettings
import com.crowdproj.resources.ktor.controller.resource
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*

fun Application.configureRouting(appSettings: CwpResourceAppSettings) {
    routing {
        static("static") {
            resources("static")
        }
        swagger(appSettings)

        authenticate("auth-jwt") {
            resource(appSettings)
        }
    }
}