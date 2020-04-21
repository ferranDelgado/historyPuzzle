package com.historypuzzle.handler

import com.historypuzzle.infrastructure.module.JacksonModule
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.websocket.*
import rx.subjects.PublishSubject
import java.util.concurrent.Executors
import javax.inject.Inject


class GameDataHandler @Inject constructor() : Handler {

    override fun handle(ctx: Context) {
        val client = ctx.request.queryParams["clientId"]
        client?.let {
            WebSockets.websocket(ctx, GameDataWebSocketHandler(it))
        } ?: run {
            throw IllegalStateException("Bad request: Missing clientId")
        }
    }
}

private val events = PublishSubject.create<String>()

class GameDataWebSocketHandler(private val clientId: String) : WebSocketHandler<String> {

    // Subject that all clients subscribe to for events

    private var scheduledExecutorService = Executors.newScheduledThreadPool(5)
    override fun onOpen(webSocket: WebSocket): String {
//        scheduledExecutorService.scheduleAtFixedRate({
//            webSocket.send("hello ${UUID.randomUUID()}")
//        }, 0, 1, TimeUnit.SECONDS)


        val connectionMessage = Message(clientId, "Client Id $clientId connected")

        events.onNext(JacksonModule.OBJECT_MAPPER.writeValueAsString(connectionMessage))
        events.subscribe {
            println("Message subscribed received $it")
            webSocket.send(it)
        }
        return "Socket connection for client $clientId"
    }

    override fun onMessage(frame: WebSocketMessage<String>) {
        println("Message received ${frame.text}")
        val message = Message(clientId, frame.text)
        events.onNext(JacksonModule.OBJECT_MAPPER.writeValueAsString(message))
    }

    override fun onClose(close: WebSocketClose<String>) {
        println("Socket closed ${close.openResult}")
    }

}


data class Message(val clientId: String, val text: String)

fun main() {
    val events = PublishSubject.create<String>()
    events.subscribe {
        println("Message received $it")
    }

    events.onNext("Hola")
    events.onNext("Adeu")

}