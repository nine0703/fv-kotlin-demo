package org.example.app.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * HTTP Client for making network requests using Ktor with OkHttp engine.
 * Supports JSON serialization/deserialization.
 */
object HttpClient {

    val ktorClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 10000
        }

        defaultRequest {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    /**
     * Perform a GET request
     */
    suspend fun get(url: String): HttpResponse {
        return ktorClient.get(url)
    }

    /**
     * Perform a POST request with JSON body
     */
    suspend inline fun <reified T> post(url: String, body: T): HttpResponse {
        return ktorClient.post(url) {
            setBody(body)
        }
    }

    /**
     * Close the client and release resources
     */
    fun close() {
        ktorClient.close()
    }
}
