package com.historypuzzle.domain

object CardReader {

    fun all(readAll: () -> List<Card>): List<Card> = readAll()
}