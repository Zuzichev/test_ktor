package com.crowdproj.resources.auth

import com.crowdproj.resources.common.model.CwpResource
import com.crowdproj.resources.common.model.CwpResourceId
import com.crowdproj.resources.common.model.CwpResourceVisibility
import com.crowdproj.resources.common.permission.CwpResourcePrincipalModel
import com.crowdproj.resources.common.permission.CwpResourcePrincipalRelations

fun CwpResource.resolveRelationsTo(principal: CwpResourcePrincipalModel): Set<CwpResourcePrincipalRelations> =
    setOfNotNull(
        CwpResourcePrincipalRelations.NONE,
        CwpResourcePrincipalRelations.NEW.takeIf { id == CwpResourceId.NONE },
        CwpResourcePrincipalRelations.OWN.takeIf { principal.id == ownerId },
        CwpResourcePrincipalRelations.MODERATABLE.takeIf { visibility != CwpResourceVisibility.VISIBLE_TO_OWNER },
        CwpResourcePrincipalRelations.PUBLIC.takeIf { visibility == CwpResourceVisibility.VISIBLE_PUBLIC },
    )