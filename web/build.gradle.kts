plugins {
    // Apply the shared build logic from a convention plugin.
    id("buildsrc.convention.kotlin-jvm")

    // Spring Boot plugin for web services
    alias(libs.plugins.spring.boot)

    // Spring Dependency Management plugin
    alias(libs.plugins.spring.dependency.management)

    // Apply Kotlin Serialization for data handling
    alias(libs.plugins.kotlinPluginSerialization)
}

dependencies {
    // Project "web" depends on other projects
    implementation(project(":utils"))
    implementation(project(":shared"))

    // Spring Boot Web - for building REST APIs and web services
    implementation(libs.bundles.springBootWeb)

    // Ktor client for making HTTP requests
    implementation(libs.bundles.ktorClient)
    implementation(libs.ktor.client.okhttp)

    // Coroutines support
    implementation(libs.kotlinx.coroutines.core)

    // Testing
    testImplementation(libs.spring.boot.starter.test)
}

// Configure Spring Boot executable JAR
springBoot {
    mainClass.set("org.example.web.ApplicationKt")
}
