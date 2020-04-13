package com.historypuzzle.infrastructure.module

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.historypuzzle.DatabaseConfig
import com.historypuzzle.common.getLogger
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin

class DatabaseModule : AbstractModule() {
    private companion object {
        val log = getLogger<DatabaseModule>()
    }
    override fun configure() {

    }

    @Singleton
    @Provides
    fun dataSource(databaseConfig: DatabaseConfig): HikariDataSource {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql://${databaseConfig.host}/${databaseConfig.db}"
        config.username = databaseConfig.user
        config.password = databaseConfig.password
        config.addDataSourceProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource")
        config.addDataSourceProperty("autoCommit", "false")
        config.addDataSourceProperty("useServerPrepStmts", "true")
        config.addDataSourceProperty("cachePrepStmts", "true")
        return HikariDataSource(config)
    }

    @Singleton
    @Provides
    fun jdbi(dataSource: HikariDataSource): Jdbi {
        return Jdbi.create(dataSource).installPlugin(KotlinPlugin())
    }
}