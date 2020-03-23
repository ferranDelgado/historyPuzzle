package com.historypuzzle

import org.amshove.kluent.shouldBeEqualTo
import ratpack.http.client.ReceivedResponse
import ratpack.test.http.TestHttpClient

fun TestHttpClient.get(path: String, test: ReceivedResponse.() -> Unit) {
    get(path).test()
}

fun TestHttpClient.postJson(
    path: String,
    json: String,
    test: ReceivedResponse.() -> Unit = { statusCode shouldBeEqualTo 200 }
) {
    requestSpec { spec ->
        spec.body { body ->
            body.type("application/json").text(json)
        }
    }.post(path).test()
}