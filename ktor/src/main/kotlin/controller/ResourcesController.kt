@file:Suppress("DuplicatedCode")
package com.crowdproj.resources.ktor.controller

import com.crowdproj.resources.ktor.CwpResourceAppSettings
import com.crowdproj.resources.ktor.controller.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.resource(appSettings: CwpResourceAppSettings) {
    val loggerResource = appSettings.corSettings.loggerProvider.logger(Route::resource::class)

    route("/api/v1/resource") {
        post("create") {
            call.createResource(appSettings, loggerResource)
        }
        post("read") {
            call.readResource(appSettings, loggerResource)
        }
        post("update") {
            call.updateResource(appSettings, loggerResource)
        }
        post("delete") {
            call.deleteResource(appSettings, loggerResource)
        }
        post("search") {
            call.searchResource(appSettings, loggerResource)
        }
    }
}