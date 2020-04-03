package com.historypuzzle.common

import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

inline fun <reified T: Any> getLogger() = LoggerFactory.getLogger(T::class.java.enclosingClass ?: T::class.java)
