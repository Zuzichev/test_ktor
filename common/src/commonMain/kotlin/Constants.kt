package com.crowdproj.resources.common

import kotlinx.datetime.Instant

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  20.13.2023 14:34
 */

private val INSTANT_NONE = Instant.fromEpochMilliseconds(Long.MIN_VALUE)

val Instant.Companion.NONE
    get() = INSTANT_NONE