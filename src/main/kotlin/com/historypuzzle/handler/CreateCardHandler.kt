package com.historypuzzle.handler

import PreconditionException
import com.historypuzzle.application.CreateCardUseCase
import com.historypuzzle.domain.Card
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.jackson.Jackson.fromJson
import ratpack.jackson.Jackson.json
import javax.inject.Inject

data class CreateCardRequest(
        val title: String,
        val year: Int,
        val month: Int?,
        val day: Int?,
        val info: String,
        val picture: String = "https://bulma.io/images/placeholders/1280x960.png",
        val wikipedia: String,
        val difficulty: Int = 0
)

class CreateCardHandler @Inject constructor(private val saver: CreateCardRequest.() -> Card) : Handler {

    override fun handle(ctx: Context) {
        ctx.parse(fromJson(CreateCardRequest::class.java))
                .map {
                    if(it.title.isEmpty()) {
                        throw PreconditionException("Title cannot be empty")
                    }
                    it
                }
                .blockingMap {
                    CreateCardUseCase.save(it, saver)
                }.then {
                    ctx.render(json(it))
                }
    }
}