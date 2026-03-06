plugins {
    // Apply the shared build logic from a convention plugin.
    id("buildsrc.convention.kotlin-jvm")

    // Kotlin Serialization for cross-platform data handling
    alias(libs.plugins.kotlinPluginSerialization)
}

dependencies {
    // Kotlinx ecosystem
    implementation(libs.bundles.kotlinxEcosystem)

    // Ktor client for network requests
    implementation(libs.bundles.ktorClient)
    implementation(libs.ktor.client.okhttp)

    // Testing
    testImplementation(kotlin("test"))
}
