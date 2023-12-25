package com.crowdproj.resources.common.helper

import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.model.CwpResourceCommand

fun CwpResourceContext.isUpdatableCommand() =
    this.command in listOf(CwpResourceCommand.CREATE, CwpResourceCommand.UPDATE, CwpResourceCommand.DELETE)
