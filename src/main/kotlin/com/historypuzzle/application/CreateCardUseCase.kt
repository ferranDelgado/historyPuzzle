package com.historypuzzle.application

import com.historypuzzle.domain.Card
import com.historypuzzle.domain.CardSaver
import com.historypuzzle.handler.CreateCardRequest

object CreateCardUseCase {
    fun save(card: CreateCardRequest, saver: CreateCardRequest.() -> Card): Card {
        return CardSaver.save(card, saver)
    }
}