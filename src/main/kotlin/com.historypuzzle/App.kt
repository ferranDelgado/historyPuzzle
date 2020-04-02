package com.historypuzzle

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.historypuzzle.handler.CreateCardHandler
import com.historypuzzle.handler.GetAllCardsHandler
import com.historypuzzle.infrastructure.CardRepository
import com.historypuzzle.infrastructure.error.GlobalErrorHandler
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.Location
import org.flywaydb.core.api.configuration.Configuration
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import ratpack.handling.Context
import ratpack.handling.RequestLogger
import ratpack.http.MutableHeaders
import ratpack.server.BaseDir
import javax.sql.DataSource


fun main() {
    createServer().start()
}

fun hikariConfig(): HikariDataSource {
    val config = HikariConfig()
    config.jdbcUrl = "jdbc:postgresql://localhost:5432/history_puzzle"
    config.username = "local-user"
    config.password = "local-password"
    config.addDataSourceProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource")
    config.addDataSourceProperty("autoCommit", "false")
    config.addDataSourceProperty("useServerPrepStmts", "true")
    config.addDataSourceProperty("cachePrepStmts", "true")
    return HikariDataSource(config)
}

private fun jdbi(datasource: DataSource = hikariConfig()): Jdbi {
    return Jdbi.create(datasource).installPlugin(KotlinPlugin())
}

private fun migrate(datasource: DataSource) {
    val flyway = Flyway.configure().dataSource(datasource).load()
    val success = flyway.migrate()
    println("Migration success $success")
}


fun createServer() = serverOf {
    serverConfig {
        baseDir(BaseDir.find())
        onError {
            println("Hello: ${it.message}")
        }
    }

    registryConfig {
        jackson {
            registerModule(KotlinModule())
        }

        onClientError(GlobalErrorHandler())
        onServerError(GlobalErrorHandler())
    }

    val hikariDataSource = hikariConfig()
    val jdbi = jdbi(hikariDataSource)
    migrate(hikariDataSource)

    val cardRepository = CardRepository(jdbi)

    readCards("C:\\Users\\ferra\\Downloads\\milestones.csv").forEach {
        cardRepository.saver(it)
    }

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
    }
}

/** A handler as a method */
fun bazHandler(context: Context) = context.render("from the baz handler")