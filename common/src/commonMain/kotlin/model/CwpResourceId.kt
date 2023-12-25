package com.crowdproj.resources.common.model

import kotlin.jvm.JvmInline

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  20.12.2023 17:10
 */

@JvmInline
value class CwpResourceId(private val id: String){
    fun asString() = id

    companion object {
        val NONE = CwpResourceId("")
    }
}