package com.historypuzzle.domain

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.inject.Singleton
//
//@Singleton
//class RoomMapper {
//    private val rooms: ConcurrentMap<RoomId, Room> = ConcurrentHashMap()
//
//    fun get(roomId: RoomId) {
//        rooms.getOrPut(roomId) {
//            Room.createFromDeck(Deck("any name", listOf()))
//        }
//    }
//}
//
//data class Deck(val name: String, val cards: List<Card>)
//
//data class RoomId(val id: String)
//
//class Player(id: String, cards: List<Card>)
//
//class Room(
//        val deck: Deck,
//        val cardsInBoard: List<Card>,
//        val cardsWaiting: List<Card>,
//        val players: List<Player>
//) {
//
//    companion object {
//        fun createFromDeck(deck: Deck): Room {
//
//        }
//    }
//}