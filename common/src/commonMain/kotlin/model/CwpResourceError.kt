package com.crowdproj.resources.common.model

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  20.12.2023 15:51
 */

data class CwpResourceError(
    var code: String = "",
    var group: String = "",
    var field: String = "",
    var title: String = "",
    var description: String = "",
    var exception: Throwable? = null,
    val level: Level = Level.ERROR,
) {
    @Suppress("unused")
    enum class Level {
        TRACE, DEBUG, INFO, WARN, ERROR
    }
}