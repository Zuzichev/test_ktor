package com.crowdproj.resources.biz.general

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.helper.errorAdministration
import com.crowdproj.resources.common.helper.fail
import com.crowdproj.resources.common.model.CwpResourceWorkMode
import com.crowdproj.resources.common.repo.IResourceRepository

fun ICorAddExecDsl<CwpResourceContext>.initRepo(title: String) = worker {
    this.title = title
    description = "Определение репозитория на основе режима работы"
    handle {
        resourceRepo = when {
            workMode == CwpResourceWorkMode.TEST -> settings.repoTest
            workMode == CwpResourceWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }

        if (workMode != CwpResourceWorkMode.STUB && resourceRepo == IResourceRepository.Companion.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "Репозиторий не настроен для $workMode"
            )
        )
    }
}