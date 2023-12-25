package com.crowdproj.resources.common.model

import kotlin.jvm.JvmInline

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  21.12.2023 12:58
 */

@JvmInline
value class CwpResourceRequestId (private val id: String) {
    fun asString() = id

    companion object {
        val NONE = CwpResourceRequestId("")
    }
}