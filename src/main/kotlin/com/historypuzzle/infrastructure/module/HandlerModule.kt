package com.historypuzzle.infrastructure.module

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.historypuzzle.handler.CreateCardHandler
import com.historypuzzle.handler.GetAllCardsHandler
import com.historypuzzle.infrastructure.CardRepository

class HandlerModule: AbstractModule() {
    override fun configure() {
    }

    @Provides
    fun getAllCardsHandler(repository: CardRepository): GetAllCardsHandler {
        return GetAllCardsHandler(repository.getAll)
    }

    @Provides
    fun createCardHandler(repository: CardRepository): CreateCardHandler {
        return CreateCardHandler(repository.saver)
    }
}