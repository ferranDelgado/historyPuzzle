package com.historypuzzle

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.historypuzzle.common.getLogger
import com.historypuzzle.handler.CreateCardHandler
import com.historypuzzle.handler.GetAllCardsHandler
import com.historypuzzle.infrastructure.module.*
import ratpack.handling.Context
import ratpack.handling.RequestLogger
import ratpack.http.MutableHeaders
import ratpack.server.BaseDir


fun main() {
    createServer().start()
}

class App
private val log = getLogger<App>()

fun createServer() = serverOf {
    registryModules(
            DatabaseModule(),
            ServicesModule(),
            HandlerModule(),
            ErrorModule(),
            JacksonModule()
    )

    serverConfig {
        jacksonModules(KotlinModule())
        yaml("config/config.json")
        require("/database", DatabaseConfig::class.java)
        baseDir(BaseDir.find())
    }

//    val cardRepository = CardRepository(jdbi)


//    readCards("C:\\Users\\ferra\\Downloads\\milestones.csv").forEach {
//        cardRepository.saver(it)
//    }

//    val createCardHandler = CreateCardHandler(cardRepository.saver)
//    val getAllCardsHandler = GetAllCardsHandler(cardRepository.getAll)

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
                it.get(GetAllCardsHandler::class.java)
                .post(CreateCardHandler::class.java)
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
    }
}

/** A handler as a method */
fun bazHandler(context: Context) = context.render("from the baz handler")