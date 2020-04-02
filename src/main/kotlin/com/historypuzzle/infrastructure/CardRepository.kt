package com.historypuzzle.infrastructure

import com.historypuzzle.domain.Card
import com.historypuzzle.handler.CreateCardRequest
import org.jdbi.v3.core.Jdbi

class CardRepository(private val jdbi: Jdbi) {
    val saver: CreateCardRequest.() -> Card = {
        jdbi.withHandle<Card, Exception> { handle ->
            val query = """
                INSERT INTO card
                (title, "year", "month", "day", difficulty, info, wikipedia_link, image_link)
                VALUES(:title, :year, :month, :day, :difficulty, :info, :wikipediaLink, :imageLink);
            """.trimIndent()
            val id = handle
                    .createUpdate(query)
                    .bind("title", title)
                    .bind("year", year)
                    .bind("month", month)
                    .bind("day", day)
                    .bind("difficulty", difficulty)
                    .bind("info", info)
                    .bind("wikipediaLink", wikipedia)
                    .bind("imageLink", picture)
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Int::class.java)
                    .first()

            Card(
                    id = id,
                    title = title,
                    year = year,
                    month = month,
                    day = day,
                    imageLink = picture,
                    wikipediaLink = wikipedia,
                    info = info
            )
        }
    }

    val getAll: () -> List<Card> = {
        jdbi.withHandle<List<Card>, Exception> {handle ->
            val query ="""
                SELECT id, title, "year", "month", "day", difficulty, info, wikipedia_link, image_link
                FROM card;
            """.trimIndent()
            handle.createQuery(query).map { rs, _ ->
                Card(
                    id = rs.getInt("id"),
                    title = rs.getString("title"),
                    year = rs.getInt("year"),
                    month = rs.getInt("month"),
                    day = rs.getInt("day"),
                    imageLink = rs.getString("image_link"),
                    wikipediaLink = rs.getString("wikipedia_link"),
                    info = rs.getString("info")
                )
            }.toList()
        }
    }
}