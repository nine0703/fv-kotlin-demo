package org.example.shared

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Shared API Service for multiplatform use.
 * This class can be used across Android, iOS, Desktop, and Web platforms.
 */
class ApiService {

    private val client = HttpClient {
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
     * Fetch user data from API
     */
    suspend fun fetchUsers(): List<User> {
        // Simulated API call - replace with actual endpoint
        return listOf(
            User(1, "Alice", "alice@example.com", Clock.System.now()),
            User(2, "Bob", "bob@example.com", Clock.System.now()),
            User(3, "Charlie", "charlie@example.com", Clock.System.now())
        )
    }

    /**
     * Fetch users as Flow for reactive programming
     */
    fun fetchUsersFlow(): Flow<List<User>> = flow {
        emit(fetchUsers())
    }

    /**
     * Create a new user
     */
    suspend fun createUser(request: CreateUserRequest): User {
        // Simulated API call - replace with actual endpoint
        return User(
            id = (4..100).random(),
            name = request.name,
            email = request.email,
            createdAt = Clock.System.now()
        )
    }

    fun close() {
        client.close()
    }
}

/**
 * User data class - shared across all platforms
 */
@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val createdAt: Instant
)

/**
 * Create user request - shared across all platforms
 */
@Serializable
data class CreateUserRequest(
    val name: String,
    val email: String
)

/**
 * API Response wrapper - shared across all platforms
 */
@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null
)
