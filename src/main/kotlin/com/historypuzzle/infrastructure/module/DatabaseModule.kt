package com.historypuzzle.infrastructure.module

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.historypuzzle.DatabaseConfig
import com.historypuzzle.common.getLogger
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import javax.sql.DataSource

class DatabaseModule : AbstractModule() {
    private companion object {
        val log = getLogger<DatabaseModule>()
    }
    override fun configure() {

    }

    @Provides
    fun dataSource(databaseConfig: DatabaseConfig): HikariDataSource {
        log.info("Creating datasource with config:")
        log.info(databaseConfig.toString())
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

    @Provides
    fun jdbi(dataSource: HikariDataSource): Jdbi {
        return Jdbi.create(dataSource).installPlugin(KotlinPlugin())
    }
}