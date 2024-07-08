package com.example.joke.service

import com.example.joke.dto.Joke
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Suppress("UNCHECKED_CAST")
@Lazy
@Service
class JokeService : BaseService() {
    @Value("\${base-url.back}")
    private val baseUrl: String? = null
    private val ten = "/ten"
    private val jokes = "/jokes"
    private val random = "/random"
    private val randomTen = "/random_ten"
    private val randomJoke = "/random_joke"

    private fun fetchJoke(url: String, responseType: Class<*>): ResponseEntity<*>? {
        return getRequest(url, null, responseType)
    }

    fun fetchOneRandomJoke(): ResponseEntity<Joke> {
        return fetchJoke(baseUrl + randomJoke, Joke::class.java) as ResponseEntity<Joke>
    }

    fun fetchRandomJoke(): ResponseEntity<Joke> {
        return fetchJoke(baseUrl + jokes + random, Joke::class.java) as ResponseEntity<Joke>
    }

    fun fetchTypeRandomJoke(type: String): ResponseEntity<ArrayList<Joke>> {
        return fetchJoke(
            baseUrl + jokes + type + random,
            Array<Joke>::class.java
        ) as ResponseEntity<ArrayList<Joke>>
    }

    fun fetchTenRandomJoke(): ResponseEntity<ArrayList<Joke>> {
        return fetchJoke(baseUrl + randomTen, Array<Joke>::class.java) as ResponseEntity<ArrayList<Joke>>
    }

    fun fetchTenRandomJokes(): ResponseEntity<ArrayList<Joke>> {
        return fetchJoke(
            baseUrl + jokes + ten,
            Array<Joke>::class.java
        ) as ResponseEntity<ArrayList<Joke>>
    }

    fun fetchTypeTenRandomJokes(type: String): ResponseEntity<ArrayList<Joke>> {
        return fetchJoke(
            baseUrl + jokes + type + ten,
            Array<Joke>::class.java
        ) as ResponseEntity<ArrayList<Joke>>
    }
}