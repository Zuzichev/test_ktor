package com.crowdproj.resources.common.exception

import com.crowdproj.resources.common.model.CwpResourceCommand

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  20.12.2023 15:43
 */

class UnknownCommandException(cmd: CwpResourceCommand): RuntimeException("Command $cmd can't be mapped and not supported.")