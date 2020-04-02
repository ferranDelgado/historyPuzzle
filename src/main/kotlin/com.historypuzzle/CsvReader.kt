package com.historypuzzle

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.historypuzzle.handler.CreateCardRequest

fun readCards(filename: String): List<CreateCardRequest> {
    return csvReader().open(filename) {
        readAllWithHeader().map {row ->
            CreateCardRequest(
                    title = row.getValue("Event "),
                    year = row.getValue("Date").toInt(),
                    month = null,
                    day = null,
                    picture = row.getValue("Image Link"),
                    wikipedia = row.getValue("Wikipedia link"),
                    info = row.getValue("Description")
            )
        }
    }
}
fun main() {
    val cards: List<CreateCardRequest> = readCards("C:\\Users\\ferra\\Downloads\\milestones.csv");
    cards.forEach {
        println(it)
    }

}

/**
    0 Creator
    1 Date
    2 Tag
    3 Difficulty
    4 Event
    5 Description
    6 Wikipedia link
    7 Image Link
 */