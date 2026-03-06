package org.example.desktop

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*

/**
 * Compose Desktop Application entry point
 * Run with: ./gradlew :desktop:run
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Desktop Demo"
    ) {
        MaterialTheme {
            App()
        }
    }
}

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, Compose Desktop!") }
    var responseText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Compose Desktop Demo") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium
            )

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter text") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    scope.launch {
                        val client = HttpClient(OkHttp)
                        try {
                            // Example: Fetch data from an API
                            val response = client.get("https://api.github.com/users/github")
                            responseText = "Status: ${response.status}"
                        } catch (e: Exception) {
                            responseText = "Error: ${e.message}"
                        } finally {
                            client.close()
                        }
                    }
                }
            ) {
                Text("Make HTTP Request")
            }

            if (responseText.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = responseText,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Feature showcase
            FeatureCard(
                title = "✅ Compose Multiplatform",
                description = "Build desktop UI with shared code"
            )
            FeatureCard(
                title = "✅ Spring Boot",
                description = "REST API and web services (in :web module)"
            )
            FeatureCard(
                title = "✅ Ktor Client",
                description = "Network requests with coroutines"
            )
            FeatureCard(
                title = "✅ Kotlinx Serialization",
                description = "JSON serialization support"
            )
        }
    }
}

@Composable
fun FeatureCard(title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
