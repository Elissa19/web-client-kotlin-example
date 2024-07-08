package com.example.joke

import com.example.joke.service.JokeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
open class AbstractTests {
    @Autowired
    lateinit var jokeService: JokeService
}