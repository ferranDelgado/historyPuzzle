package com.historypuzzle.infrastructure.module

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.inject.AbstractModule

class JacksonModule : AbstractModule() {
    override fun configure() {
        bind(ObjectMapper::class.java).toInstance(ObjectMapper().registerModule(KotlinModule()))
    }
}