package com.historypuzzle

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import ratpack.test.embed.EmbeddedApp

object CreateCardTest : Spek({

    lateinit var app: EmbeddedApp

    beforeGroup {
        app = EmbeddedApp.fromServer(createServer())
    }

    afterGroup {
        app.close()
    }

    Feature("Post new card") {
        Scenario("Happy path") {
            val jsonResult = """{"id":4,"title":"World War I","year":1914,"month":7,"day":28,"picture":"","wikipedia":"https://en.wikipedia.org/wiki/World_War_I","info":""}"""
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
                        body.text shouldBeEqualTo jsonResult
                    }
                }
            }
            Then("The new card should be part of the get method") {
                app.test { httpClient ->
                    httpClient.get("card") {
                        statusCode shouldBeEqualTo 200
                        body.text shouldContain  jsonResult
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