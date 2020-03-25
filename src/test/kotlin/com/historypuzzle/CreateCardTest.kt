package com.historypuzzle

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.spekframework.spek2.Spek
import ratpack.test.embed.EmbeddedApp

object CreateCardTest : Spek({

    lateinit var app: EmbeddedApp

    beforeGroup {
        app = EmbeddedApp.fromServer(createServer())
    }
    afterGroup {
        app.close()
    }
    group("a group") {
        test("Create card and get all") {
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
                val jsonResult = """{"id":4,"title":"World War I","year":1914,"month":7,"day":28,"picture":"","wikipedia":"https://en.wikipedia.org/wiki/World_War_I","info":""}"""
                httpClient.postJson("card", json) {
                    statusCode shouldBeEqualTo 200
                    body.text shouldBeEqualTo jsonResult
                }

                httpClient.get("card") {
                    statusCode shouldBeEqualTo 200
                    body.text shouldContain  jsonResult
                }
            }
        }
    }
})