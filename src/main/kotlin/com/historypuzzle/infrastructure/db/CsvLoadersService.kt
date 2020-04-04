package com.historypuzzle.infrastructure.db

import com.historypuzzle.App
import com.historypuzzle.common.getLogger
import com.historypuzzle.infrastructure.CardRepository
import com.historypuzzle.readCards
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import ratpack.service.Service
import ratpack.service.StartEvent
import javax.inject.Inject

class CsvLoadersService @Inject constructor(private val cardRepository: CardRepository) : Service {
    companion object {
        private val log = getLogger<App>()
    }

    override fun onStart(event: StartEvent?) {
//        val cards = readCards("C:\\Users\\ferra\\Downloads\\milestones.csv").map {
//            cardRepository.saver(it)
//        }
//        log.info("Cards created: ${cards.size}")
    }
}