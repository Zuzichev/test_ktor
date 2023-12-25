package com.crowdproj.resources.repo.postgresql

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  24.12.2023 02:10
 */

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5433/resources",
    val user: String = "postgres",
    val password: String = " postgres",
    val schema: String = "resources",
    val dropDatabase: Boolean = false,
)