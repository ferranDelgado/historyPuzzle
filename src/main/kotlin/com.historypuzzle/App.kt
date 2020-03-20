package com.historypuzzle

import ratpack.handling.Context
import ratpack.server.BaseDir


fun main() {
    createServer().start()
}

private fun createServer() = serverOf {
    serverConfig {
        baseDir(BaseDir.find())
    }

    handlers {
        files { f ->
//            f.dir("static").indexFiles("index.html")
            f.indexFiles("index.html")
        }
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

        all { render("root handler!") }
    }
}

/** A handler as a method */
fun bazHandler(context: Context) = context.render("from the baz handler")