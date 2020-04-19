package com.historypuzzle.handler

import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.websocket.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class GameDataHandler @Inject constructor() : Handler {

    override fun handle(ctx: Context) {
        WebSockets.websocket(ctx, GameDataWebSocketHandler())
    }
}

class GameDataWebSocketHandler : WebSocketHandler<String> {
    private var scheduledExecutorService = Executors.newScheduledThreadPool(5)
    override fun onOpen(webSocket: WebSocket): String {
        scheduledExecutorService.scheduleAtFixedRate({
            webSocket.send("hello ${UUID.randomUUID()}")
        }, 0, 1, TimeUnit.SECONDS)


        return "Ferran socket ${UUID.randomUUID()}"
    }

    override fun onMessage(frame: WebSocketMessage<String>) {
        println("Message received ${frame.text}")
    }

    override fun onClose(close: WebSocketClose<String>) {
        println("Socket closed ${close.openResult}")
    }

}