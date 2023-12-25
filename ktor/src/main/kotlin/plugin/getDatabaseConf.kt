package com.crowdproj.resources.ktor.plugin

import com.crowdproj.resources.common.repo.IResourceRepository
import com.crowdproj.resources.ktor.config.ConfigPaths
import com.crowdproj.resources.ktor.config.PostgresConfig
import com.crowdproj.resources.repo.inmemory.ResourceRepoInMemory
import com.crowdproj.resources.repo.postgresql.RepoResourceSQL
import com.crowdproj.resources.repo.postgresql.SqlProperties
import io.ktor.server.application.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

fun Application.getDatabaseConf(type: RatingDbType): IResourceRepository {

    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()

    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: 'inmemory', 'postgres'"
        )
    }
}

private fun Application.initPostgres(): IResourceRepository {
    val config = PostgresConfig(environment.config)
    return RepoResourceSQL(
        properties = SqlProperties(
            url = config.url,
            user = config.user,
            password = config.password,
            schema = config.schema,
        )
    )
}

private fun Application.initInMemory(): IResourceRepository {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return ResourceRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}

enum class RatingDbType(val confName: String) {
    PROD("prod"), TEST("test")
}