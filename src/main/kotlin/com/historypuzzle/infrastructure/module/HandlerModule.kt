package com.historypuzzle.infrastructure.module

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.historypuzzle.handler.CreateCardHandler
import com.historypuzzle.handler.GameDataHandler
import com.historypuzzle.handler.GetAllCardsHandler
import com.historypuzzle.infrastructure.CardRepository

class HandlerModule: AbstractModule() {
    override fun configure() {
    }

    @Singleton
    @Provides
    fun getAllCardsHandler(repository: CardRepository): GetAllCardsHandler {
        return GetAllCardsHandler(repository.getAll)
    }

    @Singleton
    @Provides
    fun createCardHandler(repository: CardRepository): CreateCardHandler {
        return CreateCardHandler(repository.saver)
    }

    @Singleton
    @Provides
    fun createGameDataHandler(): GameDataHandler {
        return GameDataHandler()
    }
}