package com.historypuzzle.domain

data class Card(
        val id: Int,
        val title: String,
        val year: Int,
        val month: Int?,
        val day: Int?,
        val imageLink: String,
        val wikipediaLink: String,
        val info: String
)