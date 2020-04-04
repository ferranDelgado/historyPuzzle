package com.historypuzzle.infrastructure.module

import com.google.inject.AbstractModule
import com.historypuzzle.infrastructure.db.CsvLoadersService
import com.historypuzzle.infrastructure.db.DatabaseMigrationService


class ServicesModule : AbstractModule() {
    override fun configure() {
        bind(DatabaseMigrationService::class.java)
        bind(CsvLoadersService::class.java)
    }
}

