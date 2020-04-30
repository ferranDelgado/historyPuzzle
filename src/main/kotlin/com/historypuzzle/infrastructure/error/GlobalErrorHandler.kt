package com.historypuzzle.infrastructure.error

import AppException
import PreconditionException
import com.historypuzzle.common.getLogger
import ratpack.error.internal.ErrorHandler
import ratpack.handling.Context
import ratpack.jackson.Jackson.json

private data class ErrorResponse(val code: Int, val message: String)

class GlobalErrorHandler : ErrorHandler {
    companion object {
        val log = getLogger<GlobalErrorHandler>()
    }
    override fun error(context: Context?, statusCode: Int) {
        log.error("Error: $statusCode")
    }

    override fun error(context: Context, throwable: Throwable) {
        throwable.printStackTrace()
        val appException: AppException? = throwable.cause as? AppException
        val response: ErrorResponse = when (appException) {
            is PreconditionException -> ErrorResponse(appException.code, appException.message)
            null -> ErrorResponse(505, throwable.message ?: "")
        }

        context.response.status(response.code)
        context.render(json(response))
    }

}