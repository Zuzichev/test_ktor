package com.crowdproj.resources.ktor

import com.crowdproj.resources.biz.CwpResourceProcessor
import com.crowdproj.resources.common.CwpResourceCorSettings
import com.crowdproj.resources.ktor.base.KtorAuthConfig

data class CwpResourceAppSettings(
    val appUrls: List<String> = emptyList(),
    val corSettings: CwpResourceCorSettings,
    val processor: CwpResourceProcessor = CwpResourceProcessor(settings = corSettings),
    val auth: KtorAuthConfig = KtorAuthConfig.NONE,
)