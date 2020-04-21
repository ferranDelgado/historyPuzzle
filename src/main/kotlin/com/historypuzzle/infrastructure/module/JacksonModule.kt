package com.historypuzzle.infrastructure.module

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.inject.AbstractModule

class JacksonModule : AbstractModule() {
    companion object {
        val OBJECT_MAPPER = ObjectMapper().registerModule(KotlinModule())
    }

    override fun configure() {
        bind(ObjectMapper::class.java).toInstance(OBJECT_MAPPER)
    }
}