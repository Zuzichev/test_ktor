package com.crowdproj.resources.common.model

import kotlin.jvm.JvmInline

@JvmInline
value class CwpOtherResourceId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = CwpOtherResourceId("")
    }
}