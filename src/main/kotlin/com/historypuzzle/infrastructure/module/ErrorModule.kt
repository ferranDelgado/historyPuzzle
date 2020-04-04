package com.historypuzzle.infrastructure.module

import com.google.inject.AbstractModule
import com.historypuzzle.infrastructure.error.GlobalErrorHandler
import ratpack.error.ClientErrorHandler
import ratpack.error.ServerErrorHandler

class ErrorModule:  AbstractModule() {
    override fun configure() {
        bind(ClientErrorHandler::class.java).to(GlobalErrorHandler::class.java)
        bind(ServerErrorHandler::class.java).to(GlobalErrorHandler::class.java)
    }
}