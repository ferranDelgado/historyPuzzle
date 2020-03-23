package com.historypuzzle

import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import ratpack.test.embed.EmbeddedApp

object CreateCardTest : Spek({
    val app = EmbeddedApp.fromServer(createServer())
    group("a group") {
        test("Create card and get all") {
            app.test { httpClient ->
                httpClient.postJson("card", "{\"title\":\"John\"}") {
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