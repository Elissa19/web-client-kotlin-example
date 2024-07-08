package com.example.joke

import com.example.joke.dto.Joke
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.http.ResponseEntity

internal class JokeTests : AbstractTests() {
    @Test
    @DisplayName("Get one random joke")
    fun fetchOneRandomJoke() {
        val response: ResponseEntity<Joke> = jokeService.fetchOneRandomJoke()
        response.statusCode.is2xxSuccessful
        Assertions.assertNotNull(response.body)
    }

    @Test
    @DisplayName("Get one random joke second way")
    fun fetchRandomJoke() {
        val response: ResponseEntity<Joke> = jokeService.fetchRandomJoke()
        response.statusCode.is2xxSuccessful
        Assertions.assertNotNull(response.body)
    }

    @ParameterizedTest
    @MethodSource("types")
    @DisplayName("Get one random joke by type")
    fun fetchTypeOneRandomJoke(type: String) {
        val response: ResponseEntity<ArrayList<Joke>> = jokeService.fetchTypeRandomJoke(type)
        response.statusCode.is2xxSuccessful
        Assertions.assertNotNull(response.body)
    }

    @Test
    @DisplayName("Get ten random jokes")
    fun fetchTenRandomJoke() {
        val response: ResponseEntity<ArrayList<Joke>> = jokeService.fetchTenRandomJoke()
        response.statusCode.is2xxSuccessful
        Assertions.assertNotNull(response.body)
    }

    @Test
    @DisplayName("Get ten random jokes second way")
    fun fetchTenRandomJokes() {
        val response: ResponseEntity<ArrayList<Joke>> = jokeService.fetchTenRandomJokes()
        response.statusCode.is2xxSuccessful
        Assertions.assertNotNull(response.body)
    }

    @ParameterizedTest
    @MethodSource("types")
    @DisplayName("Get ten random jokes by type")
    fun fetchTypeTenRandomJoke(type: String) {
        val response: ResponseEntity<ArrayList<Joke>> = jokeService.fetchTypeTenRandomJokes(type)
        response.statusCode.is2xxSuccessful
        Assertions.assertNotNull(response.body)
    }

    companion object {
        @JvmStatic
        fun types() = listOf(
            Arguments.of("/programming"),
            Arguments.of("/knock-knock"),
            Arguments.of("/general"),
            Arguments.of("/dad")
        )
    }
}