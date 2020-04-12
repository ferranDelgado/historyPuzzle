package com.historypuzzle.handler

import com.historypuzzle.domain.Card
import com.historypuzzle.domain.CardReader
import ratpack.exec.Blocking
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.jackson.Jackson
import javax.inject.Inject

class GetAllCardsHandler @Inject constructor(private val getAllCards: () -> List<Card>) : Handler {

    override fun handle(ctx: Context) {
        Blocking.get {
            CardReader.all(getAllCards)
        }.then {
            ctx.render(Jackson.json(it))
        }
    }
}