package com.historypuzzle.handler

import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.fasterxml.jackson.annotation.JsonValue
import com.historypuzzle.common.getLogger
import com.historypuzzle.infrastructure.module.JacksonModule
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.websocket.*
import rx.subjects.PublishSubject
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject


class GameDataHandler @Inject constructor() : Handler {

    override fun handle(ctx: Context) {
//        WebSockets.websocket(ctx, GameDataWebSocketHandler())
        val userName = ctx.request.queryParams["userName"]
        userName?.let {
            WebSockets.websocket(ctx, GameDataWebSocketHandler(UserName(it)))
        } ?: run {
            throw IllegalStateException("Bad request: Missing clientId")
        }
    }
}

private val events = PublishSubject.create<SocketEvent>()

data class ConnectionId(
        @JsonValue
        val value: String = UUID.randomUUID().toString())
data class UserName(@JsonValue val value: String)

class GameDataWebSocketHandler(private val userName: UserName, private val connectionId: ConnectionId = ConnectionId()) : WebSocketHandler<String> {
    companion object {
        private val log = getLogger<GameDataWebSocketHandler>()
    }

    // Subject that all clients subscribe to for events
    private var isConnected = false

    private var scheduledExecutorService = Executors.newScheduledThreadPool(5)
    override fun onOpen(webSocket: WebSocket): String {
        log.info("Socket connection open [Id: $connectionId]")
        if(!isConnected) {
            isConnected = true
            events.onNext(OpenEvent(connectionId, userName))

            events.subscribe {
                webSocket.send(it)
            }
        }

        return connectionId.value
    }

    override fun onMessage(frame: WebSocketMessage<String>) {
        log.info("Message received [id: $connectionId, message: ${frame.text}]")
        val message = MessageEvent(connectionId, userName, frame.text)
        events.onNext(message)
    }

    override fun onClose(close: WebSocketClose<String>) {
        log.info("Socket closed ${close.openResult}")
        val closeEvent = CloseEvent(connectionId = connectionId, userName = userName)
        events.onNext(closeEvent)
    }
}

private fun WebSocket.send(event: SocketEvent) {
    send(JacksonModule.OBJECT_MAPPER.writeValueAsString(event))
}

enum class EventType {
    OPEN_EVENT,
    CLOSE_EVENT
}

sealed class SocketEvent(val type: EventType, open val connectionId: ConnectionId, open val userName: UserName)
data class OpenEvent(override val connectionId: ConnectionId, override val userName: UserName) : SocketEvent(EventType.OPEN_EVENT, connectionId, userName)
data class CloseEvent(override val connectionId: ConnectionId, override val userName: UserName) : SocketEvent(EventType.CLOSE_EVENT, connectionId, userName)
data class MessageEvent(override val connectionId: ConnectionId, override val userName: UserName, val message: String) : SocketEvent(EventType.CLOSE_EVENT, connectionId, userName)


data class Message(val clientId: String, val text: String)

fun main() {
    val events = PublishSubject.create<String>()
    events.subscribe {
        println("Message received $it")
    }

    events.onNext("Hola")
    events.onNext("Adeu")

}