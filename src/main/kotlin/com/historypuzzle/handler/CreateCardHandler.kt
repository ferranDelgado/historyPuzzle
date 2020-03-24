package com.historypuzzle.handler

import com.historypuzzle.application.CreateCardUseCase
import com.historypuzzle.domain.Card
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.jackson.Jackson.fromJson
import ratpack.jackson.Jackson.json

data class CreateCardRequest(
    val title: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val picture: String,
    val wikipedia: String
)

class CreateCardHandler(private val saver: CreateCardRequest.() -> Card): Handler {

    override fun handle(ctx: Context) {
        ctx.parse(fromJson(CreateCardRequest::class.java)).map {
            CreateCardUseCase.save(it, saver)
        }.then {
            ctx.render(json(it))
        }
    }
}