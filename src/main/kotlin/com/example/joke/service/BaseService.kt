package com.example.joke.service

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.SneakyThrows
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Lazy
@Service
open class BaseService {
    @Autowired
    lateinit var webClient: WebClient

    @Autowired
    lateinit var objectMapper: ObjectMapper

    fun getRequest(
        path: String,
        request: HttpEntity<*>?,
        responseType: Class<*>
    ): ResponseEntity<*>? {
        logDataRequest(request)
        val response: ResponseEntity<*>? = webClient.get()
            .uri(path)
            .retrieve()
            .toEntity(responseType)
            .block()
        if (response != null) {
            logDataResponse(response)
        }
        return response
    }

    fun putRequest(
        path: String,
        urlVariables: Map<String, *>,
        request: HttpEntity<*>,
        responseType: Class<*>
    ): ResponseEntity<*>? {
        logDataRequest(request)
        val response: ResponseEntity<*>? = webClient.put()
            .uri(path + paramsToRequest(urlVariables))
            .bodyValue(request)
            .retrieve()
            .toEntity(responseType)
            .block()
        if (response != null) {
            logDataResponse(response)
        }
        return response
    }

    fun postRequest(
        path: String,
        request: HttpEntity<*>,
        responseType: Class<*>
    ): ResponseEntity<*>? {
        logDataRequest(request)
        val response: ResponseEntity<*>? = webClient.post()
            .uri(path)
            .bodyValue(request)
            .retrieve()
            .toEntity(responseType)
            .block()
        return response
    }

    private fun paramsToRequest(urlVariables: Map<String, *>): String {
        val params = StringBuilder("?")
        for (k in urlVariables.keys) {
            params.append(k).append("{").append(k).append("}&")
        }
        return params.toString()
    }

    @SneakyThrows
    private fun logDataRequest(request: HttpEntity<*>?) {
        if (request == null) {
            logger.info("Request body: null")
            logger.info("Request headers: null")
        } else {
            logger.info("Request headers: {}", objectMapper.writeValueAsString(request.headers.toString()))
            logger.info("Request body: {}", objectMapper.writeValueAsString(request.headers.toString()))
        }
    }

    @SneakyThrows
    private fun logDataResponse(response: ResponseEntity<*>) {
        logger.info("Response headers: {}", objectMapper.writeValueAsString(response.headers))
        logger.info("Response body: {}", objectMapper.writeValueAsString(response.body))
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BaseService::class.java)
    }
}