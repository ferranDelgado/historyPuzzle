package com.historypuzzle

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.historypuzzle.handler.CreateCardHandler
import com.historypuzzle.handler.GetAllCardsHandler
import com.historypuzzle.infrastructure.CardRepository
import ratpack.handling.Context
import ratpack.handling.RequestLogger
import ratpack.http.MutableHeaders
import ratpack.server.BaseDir


fun main() {
    createServer().start()
}

fun createServer() = serverOf {
    serverConfig {
        baseDir(BaseDir.find())
    }

    jacksonConfig {
        registerModule(KotlinModule())
    }

    val cardRepository = CardRepository()
    val createCardHandler = CreateCardHandler(cardRepository.saver)
    val getAllCardsHandler = GetAllCardsHandler(cardRepository.getAll)

    handlers {
        files { f ->
            f.indexFiles("index.html")
        }

        all {
            val headers: MutableHeaders = response.headers
            headers.set("Access-Control-Allow-Origin", "*")
            headers.set("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept")
            next()
        }

        all(RequestLogger.ncsa())
        path("card") { ctx ->
            ctx.byMethod {
                it.get(getAllCardsHandler)
                .post(createCardHandler)
            }

        }

        get("hola") { render("hola") }
        path("foo") { render("from the foo handler") }
        path("bar") { render("from the bar handler") }

        // Map to /baz using a Kotlin function
        path("baz", ::bazHandler)

        // Set up a nested routing block, which is delegated to `nestedHandler`
        prefix("nested") {
            it.path(":var1/:var2?") { ctx ->
                // The path tokens are the :var1 and :var2 path components above
                val var1 = ctx.pathTokens["var1"]
                val var2 = ctx.pathTokens["var2"]
                ctx.render("from the nested handler, var1: $var1, var2: $var2")
            }
        }

        // Map to a dependency injected handler
        //path("injected", MyHandler::class.java)

        // Bind the /static app path to the src/ratpack/assets/images dir
//        prefix("static") {
//            fileSystem("assets/images") { files() }
//        }

//        all { render("root handler!") }
    }
}

/** A handler as a method */
fun bazHandler(context: Context) = context.render("from the baz handler")