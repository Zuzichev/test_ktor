package com.crowdproj.resources.biz.permission

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.resources.auth.resolveFrontPermissions
import com.crowdproj.resources.auth.resolveRelationsTo
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceState

fun ICorAddExecDsl<CwpResourceContext>.frontPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда."

    on { state == CwpResourceState.RUNNING }

    handle {
        resourceRepoDone.permissionsClient.addAll(
            resolveFrontPermissions(
                permissionsChain,
                // Повторно вычисляем отношения, поскольку они могли измениться при выполении операции
                resourceRepoDone.resolveRelationsTo(principal)
            )
        )

        for (rating in resourcesRepoDone) {
            rating.permissionsClient.addAll(
                resolveFrontPermissions(
                    permissionsChain,
                    rating.resolveRelationsTo(principal)
                )
            )
        }
    }
}