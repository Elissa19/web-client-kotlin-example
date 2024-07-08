package com.example.joke

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient
import java.util.concurrent.TimeUnit

@SpringBootApplication
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class, HibernateJpaAutoConfiguration::class])
class AppConfig {
    @Value("\${base-url.back}")
    private lateinit var BASE_URL: String
    @Bean
    fun webClient(): WebClient {
        val tcpClient: TcpClient = TcpClient
            .create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT.toInt())
            .doOnConnected { connection ->
                connection.addHandlerLast(
                    ReadTimeoutHandler(
                        TIMEOUT,
                        TimeUnit.MILLISECONDS
                    )
                )
                connection.addHandlerLast(
                    WriteTimeoutHandler(
                        TIMEOUT,
                        TimeUnit.MILLISECONDS
                    )
                )
            }

        return WebClient.builder()
            .baseUrl(BASE_URL)
            .clientConnector(ReactorClientHttpConnector(HttpClient.from(tcpClient)))
            .build()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(AppConfig::class.java, *args)
        }

        const val TIMEOUT: Long = 10000
    }
}