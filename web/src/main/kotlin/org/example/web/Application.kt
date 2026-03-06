package org.example.web

import kotlinx.serialization.Serializable
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*

/**
 * Spring Boot Application entry point
 * Run with: ./gradlew :web:bootRun
 */
@SpringBootApplication
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

/**
 * REST API Controller for demo endpoints
 */
@RestController
@RequestMapping("/api")
class DemoController {

    /**
     * GET /api/hello - Returns a greeting message
     */
    @GetMapping("/hello")
    fun hello(@RequestParam(defaultValue = "World") name: String): Message {
        return Message("Hello, $name! Welcome to Spring Boot with Kotlin.")
    }

    /**
     * GET /api/users - Returns a list of users
     */
    @GetMapping("/users")
    fun getUsers(): List<User> {
        return listOf(
            User(1, "Alice", "alice@example.com"),
            User(2, "Bob", "bob@example.com"),
            User(3, "Charlie", "charlie@example.com")
        )
    }

    /**
     * POST /api/users - Creates a new user
     */
    @PostMapping("/users")
    fun createUser(@RequestBody request: CreateUserRequest): User {
        return User(
            id = (4..100).random(),
            name = request.name,
            email = request.email
        )
    }

    /**
     * GET /api/status - Returns application status
     */
    @GetMapping("/status")
    fun getStatus(): Status {
        return Status(
            status = "UP",
            version = "1.0.0",
            kotlinVersion = KotlinVersion.CURRENT.toString()
        )
    }
}

// Data classes for API responses/requests
@Serializable
data class Message(val message: String)

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String
)

@Serializable
data class CreateUserRequest(
    val name: String,
    val email: String
)

@Serializable
data class Status(
    val status: String,
    val version: String,
    val kotlinVersion: String
)
