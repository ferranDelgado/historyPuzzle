package com.historypuzzle.common

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertFailsWith

object HerokuUrlDbToPropertiesTest : Spek({
    describe("correct url format") {
        it("uri is split with url, password and user") {
            val url = "postgres://userValue:passwordValue@host.url.dot.com:5432/databaseName"
            val properties = HerokuUrlDbToProperties.extractDbProperties(url)
            properties.size shouldBeEqualTo 5
            properties shouldContain "database.user=userValue"
            properties shouldContain "database.password=passwordValue"
            properties shouldContain "database.host=host.url.dot.com"
            properties shouldContain "database.port=5432"
            properties shouldContain "database.db=databaseName"
        }
    }

    describe("incorrect format") {
        it("Incorrect format") {
            val url = "postgres://wrongformat"
            assertFailsWith<IllegalStateException>("Incorrect database url format") {
                HerokuUrlDbToProperties.extractDbProperties(url)
            }
        }
    }

    describe("incorrect or empty format") {
        it("Empty url") {
            val url = ""
            val properties = HerokuUrlDbToProperties.extractDbProperties(url)
            properties.size shouldBeEqualTo 0
        }
    }
})