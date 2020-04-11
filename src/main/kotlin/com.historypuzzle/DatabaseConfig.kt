package com.historypuzzle

data class DatabaseConfig(
        val host: String = "localhost:5432",
        val db: String = "history_puzzle",
        val user: String = "local_user",
        val password: String
)