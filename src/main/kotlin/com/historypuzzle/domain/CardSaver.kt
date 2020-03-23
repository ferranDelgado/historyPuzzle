package com.historypuzzle.domain

import com.historypuzzle.handler.CreateCardRequest

object CardSaver {

    fun save(card: CreateCardRequest, saver: CreateCardRequest.() -> Card): Card {
        return card.saver()
    }
}