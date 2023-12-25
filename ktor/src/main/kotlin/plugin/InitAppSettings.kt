package com.crowdproj.resources.ktor.plugin

import com.crowdproj.resources.biz.CwpResourceProcessor
import com.crowdproj.resources.common.CwpResourceCorSettings
import com.crowdproj.resources.ktor.CwpResourceAppSettings
import com.crowdproj.resources.ktor.base.KtorAuthConfig
import com.crowdproj.resources.repo.stubs.ResourceRepoStub
import io.ktor.server.application.*

fun Application.initAppSettings(): CwpResourceAppSettings {

    val corSettings = CwpResourceCorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoTest = getDatabaseConf(RatingDbType.TEST),
        repoProd = getDatabaseConf(RatingDbType.PROD),
        repoStub = ResourceRepoStub(),
    )

    return CwpResourceAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = CwpResourceProcessor(corSettings),
        auth = initAppAuth(),
    )
}

private fun Application.initAppAuth(): KtorAuthConfig = KtorAuthConfig(
    secret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "",
    issuer = environment.config.property("jwt.issuer").getString(),
    audience = environment.config.property("jwt.audience").getString(),
    realm = environment.config.property("jwt.realm").getString(),
    clientId = environment.config.property("jwt.clientId").getString(),
    certUrl = environment.config.propertyOrNull("jwt.certUrl")?.getString(),
)