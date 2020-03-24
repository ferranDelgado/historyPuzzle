package com.historypuzzle

import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import ratpack.test.embed.EmbeddedApp

object CreateCardTest : Spek({
//    val app = EmbeddedApp.fromServer(createServer())
    group("a group") {
        test("Create card and get all") {
            EmbeddedApp.fromServer(createServer()).test { httpClient ->
                val json = """
                    {
                        title: "World War I",
                        year: 1914,
                        month: 7,
                        day: 28,
                        picture: "",
                        wikipedia: "https://en.wikipedia.org/wiki/World_War_I"
                    }
                """
                httpClient.postJson("card", json) {
                    statusCode shouldBeEqualTo 200
                    body.text shouldBeEqualTo """{"id":1,"title":"John"}"""
                }

                httpClient.get("card") {
                    statusCode shouldBeEqualTo 200
                    body.text shouldBeEqualTo """[{"id":1,"title":"John"}]"""
                }
            }
        }
    }
})