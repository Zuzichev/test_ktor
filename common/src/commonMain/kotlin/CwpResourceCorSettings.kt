package com.crowdproj.resources.common

import com.crowdproj.resources.logging.common.CwpLoggerProvider
import com.crowdproj.resources.common.repo.IResourceRepository

data class CwpResourceCorSettings(
    val loggerProvider: CwpLoggerProvider = CwpLoggerProvider(),
    val repoStub: IResourceRepository = IResourceRepository.NONE,
    val repoTest: IResourceRepository = IResourceRepository.NONE,
    val repoProd: IResourceRepository = IResourceRepository.NONE,
) {
    companion object {
        val NONE = CwpResourceCorSettings()
    }
}