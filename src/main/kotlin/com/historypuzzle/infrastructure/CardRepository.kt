package com.historypuzzle.infrastructure

import com.historypuzzle.domain.Card
import com.historypuzzle.handler.CreateCardRequest
import java.util.concurrent.atomic.AtomicInteger

class CardRepository {
    //Fake Db
    private val db: MutableMap<Int, Card> = mutableMapOf(
        1 to Card(
            id = 1,
            title = "World War I",
            year = 1914,
            month = 7,
            day = 28,
            picture = "",
            wikipedia = "https://en.wikipedia.org/wiki/World_War_I"
        ),
        2 to Card(
            id = 2,
            title = "World War II",
            year = 1945,
            month = 9,
            day = 1,
            picture = "",
            wikipedia = "https://en.wikipedia.org/wiki/World_War_II"
        )
    )
    private val autoId = AtomicInteger(3)

    val saver: CreateCardRequest.() -> Card = {
        val newId = autoId.incrementAndGet()
        val copy = Card(
            id = newId,
            title = title,
            year = 1945,
            month = 9,
            day = 1,
            picture = "",
            wikipedia = "https://en.wikipedia.org/wiki/World_War_II"
        )
        db[newId] = copy
        copy
    }

    val getAll: () -> List<Card> = { db.values.toList() }
}