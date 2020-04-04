package com.historypuzzle.infrastructure.db

import com.historypuzzle.App
import com.historypuzzle.common.getLogger
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import ratpack.service.Service
import ratpack.service.StartEvent
import javax.inject.Inject

class DatabaseMigrationService @Inject constructor(private val dataSource: HikariDataSource) : Service {
    companion object {
        private val log = getLogger<App>()
    }

    override fun onStart(event: StartEvent?) {
        super.onStart(event)
        val flyway = Flyway.configure().dataSource(dataSource).load()
        val success = flyway.migrate()
        log.info("Successfully applied migrations $success ")
    }
}