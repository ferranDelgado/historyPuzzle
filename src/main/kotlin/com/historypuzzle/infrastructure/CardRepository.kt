package com.historypuzzle.infrastructure

import com.historypuzzle.domain.Card
import com.historypuzzle.handler.CreateCardRequest
import java.util.concurrent.atomic.AtomicInteger

class CardRepository {
    //Fake Db
    private val db: MutableMap<Int, Card> = mutableMapOf()
    private val autoId = AtomicInteger(0)

    val saver: CreateCardRequest.() -> Card = {
        val newId = autoId.incrementAndGet()
        val copy = Card(id = newId, title = title)
        db[newId] = copy
        copy
    }

    val getAll: () -> List<Card> = { db.values.toList() }
}