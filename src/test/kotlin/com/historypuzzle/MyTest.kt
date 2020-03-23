package com.historypuzzle

import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import ratpack.test.embed.EmbeddedApp
import ratpack.test.http.TestHttpClient

object MyTest : Spek({
    val app = EmbeddedApp.fromServer(createServer())
    group("a group") {
        test("a test") {
            app.test { httpClient: TestHttpClient ->
                val response = httpClient.get("/hola")
                response.statusCode shouldBeEqualTo 200
                println(response.body.text)
                response.body.text shouldBeEqualTo "hola"
            }
        }

        test("create card") {
            app.test { httpClient ->
                val createCardResponse = httpClient.requestSpec { spec ->
                    spec.body { body ->
                        body.type("application/json").text("{\"title\":\"John\"}")
                    }
                }.post("card");

                createCardResponse.statusCode shouldBeEqualTo 200
                createCardResponse.body.text shouldBeEqualTo """{"id":1,"title":"John"}"""

                val listResponse = httpClient.get("card")
                listResponse.statusCode shouldBeEqualTo 200
                listResponse.body.text shouldBeEqualTo """[{"id":1,"title":"John"}]"""
            }
        }

        group("a nested group") {
            test("another test") {
                33 shouldBeEqualTo 66
            }
        }
    }
})