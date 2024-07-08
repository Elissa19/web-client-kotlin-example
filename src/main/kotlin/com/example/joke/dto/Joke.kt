package com.example.joke.dto

data class Joke(
    val id: Int,
    val type: String,
    val setup: String,
    val punchline: String
)