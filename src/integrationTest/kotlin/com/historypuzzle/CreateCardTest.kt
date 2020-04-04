package com.historypuzzle

import com.fasterxml.jackson.databind.ObjectMapper
import org.amshove.kluent.shouldBeEqualTo
import org.jdbi.v3.core.Jdbi
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.specification.describe
import ratpack.test.embed.EmbeddedApp

object CreateCardTest : Spek({

    describe("tests test") {
        it("patata") {
            3 shouldBeEqualTo 3
        }
    }
    lateinit var app: EmbeddedApp

    beforeGroup {
        /**
         * This should set up a specific db for integration test
         */
        val db = "history_puzzle"
        val user = "local-user"
        val password = "local-password"
        val url = "jdbc:postgresql://localhost:5432/$db"
        System.setProperty("ratpack.database.db", db)
        System.setProperty("ratpack.database.user", "local-user")
        System.setProperty("ratpack.database.password", "local-password")

        val jdbi = Jdbi.create(url, user, password)

        jdbi.useHandle<Exception> {
            it.createUpdate("DELETE FROM card").execute()
        }
        app = EmbeddedApp.fromServer(createServer())
    }

    afterGroup {
        app.close()
    }

    Feature("Post new card") {
        Scenario("Happy path") {
            When("Correct json is send") {
                app.test { httpClient ->
                    val json = """
                    {
                        "title": "World War I",
                        "year": 1914,
                        "month": 7,
                        "day": 28,
                        "picture": "",
                        "info": "",
                        "wikipedia": "https://en.wikipedia.org/wiki/World_War_I"
                    }
                """
                    httpClient.postJson("card", json) {
                        statusCode shouldBeEqualTo 200
                        val jsonMap = ObjectMapper().readValue(body.text, Map::class.java)
                        jsonMap.keys.size shouldBeEqualTo 8
                        jsonMap["title"] shouldBeEqualTo "World War I"
                        jsonMap["year"] shouldBeEqualTo 1914
                        jsonMap["month"] shouldBeEqualTo  7
                        jsonMap["day"] shouldBeEqualTo  28
                        jsonMap["imageLink"] shouldBeEqualTo  ""
                        jsonMap["info"] shouldBeEqualTo  ""
                        jsonMap["wikipediaLink"] shouldBeEqualTo  "https://en.wikipedia.org/wiki/World_War_I"
                    }
                }
            }
            Then("The new card should be part of the get method") {
                app.test { httpClient ->
                    httpClient.get("card") {
                        statusCode shouldBeEqualTo 200
                        val jsonList = ObjectMapper().readValue(body.text, List::class.java)
                        jsonList.size shouldBeEqualTo 1
                        val jsonMap = jsonList[0] as Map<String, Any>
                        jsonMap.keys.size shouldBeEqualTo 8
                        jsonMap["title"] shouldBeEqualTo "World War I"
                        jsonMap["year"] shouldBeEqualTo 1914
                        jsonMap["month"] shouldBeEqualTo  7
                        jsonMap["day"] shouldBeEqualTo  28
                        jsonMap["imageLink"] shouldBeEqualTo  ""
                        jsonMap["info"] shouldBeEqualTo  ""
                        jsonMap["wikipediaLink"] shouldBeEqualTo  "https://en.wikipedia.org/wiki/World_War_I"
                    }
                }
            }
        }

        Scenario("Empty title should fail") {
            When("Card with empty title") {
                app.test { httpClient ->
                    val json = """
                    {
                        "title": "",
                        "year": 1914,
                        "month": 7,
                        "day": 28,
                        "picture": "",
                        "info": "",
                        "wikipedia": "https://en.wikipedia.org/wiki/World_War_I"
                    }
                """
                    httpClient.postJson("card", json) {
                        body.text shouldBeEqualTo """{"code":400,"message":"Title cannot be empty"}"""
                        statusCode shouldBeEqualTo 400
                        body.text shouldBeEqualTo """{"code":400,"message":"Title cannot be empty"}"""
                    }
                }
            }
        }
    }
})