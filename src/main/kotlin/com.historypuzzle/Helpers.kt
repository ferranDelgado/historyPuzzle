package com.historypuzzle


/*
 * Helper functions to make the example more Kotlin like.
 */

import com.fasterxml.jackson.databind.ObjectMapper
import ratpack.error.ClientErrorHandler
import ratpack.error.ServerErrorHandler
import ratpack.error.internal.ErrorHandler
import ratpack.handling.Chain
import ratpack.handling.Context
import ratpack.registry.RegistrySpec
import ratpack.registry.internal.DefaultRegistryBuilder
import ratpack.server.RatpackServer
import ratpack.server.RatpackServerSpec
import ratpack.server.ServerConfigBuilder

fun serverOf(cb: KServerSpec.() -> Unit) = RatpackServer.of { KServerSpec(it).cb() }
fun serverStart(cb: KServerSpec.() -> Unit) = RatpackServer.start { KServerSpec(it).cb() }

class KChain(val delegate: Chain) : Chain by delegate {
    fun fileSystem(path: String = "", cb: KChain.() -> Unit) =
        delegate.fileSystem(path) { KChain(it).cb() }

    fun prefix(path: String = "", cb: KChain.() -> Unit) =
        delegate.prefix(path) { KChain(it).cb() }

    fun all(cb: Context.() -> Unit) = delegate.all { it.cb() }
    fun path(path: String = "", cb: Context.() -> Unit) = delegate.path(path) { it.cb() }

    @Suppress("ReplaceGetOrSet")
    fun get(path: String = "", cb: Context.() -> Unit) = delegate.get(path) { it.cb() }

    fun put(path: String = "", cb: Context.() -> Unit) = delegate.put(path) { it.cb() }
    fun post(path: String = "", cb: Context.() -> Unit) = delegate.post(path) { it.cb() }
    fun delete(path: String = "", cb: Context.() -> Unit) = delegate.delete(path) { it.cb() }
    fun options(path: String = "", cb: Context.() -> Unit) = delegate.options(path) { it.cb() }
    fun patch(path: String = "", cb: Context.() -> Unit) = delegate.patch(path) { it.cb() }
}

class KContext(val delegate: Context) : Context by delegate

class KRegistrySpec(private val delegate: RegistrySpec) {

    fun onClientError(handler: ErrorHandler) {
        delegate.add<ClientErrorHandler>(ClientErrorHandler::class.java, handler)
    }

    fun onServerError(handler: ErrorHandler) {
        delegate.add<ServerErrorHandler>(ServerErrorHandler::class.java, handler)
    }

    fun jackson(cb: ObjectMapper.() -> Unit) {
        val objectMapper = ObjectMapper()
        objectMapper.cb()
        delegate.add(ObjectMapper::class.java, objectMapper)
    }
}

class KServerSpec(val delegate: RatpackServerSpec) {

    fun registryConfig(cb: KRegistrySpec.() -> Unit) = delegate.registryOf { registry ->
        KRegistrySpec(registry).cb()
    }

    fun serverConfig(cb: ServerConfigBuilder.() -> Unit) = delegate.serverConfig { it.cb() }
    fun handlers(cb: KChain.() -> Unit) = delegate.handlers { KChain(it).cb() }
}