package com.historypuzzle.domain

data class Card(
    val id: Int,
    val title: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val picture: String,
    val wikipedia: String
)