package com.crowdproj.resources.common.exception

import kotlin.reflect.KClass

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  20.12.2023 15:44
 */

class UnknownRequestException(cls: KClass<*>): RuntimeException("Class $cls can't be mapped and not supported.")