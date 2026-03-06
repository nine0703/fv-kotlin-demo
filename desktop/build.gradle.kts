plugins {
    // Apply the shared build logic from a convention plugin.
    id("buildsrc.convention.kotlin-jvm")

    // Compose Multiplatform plugin for desktop UI
    alias(libs.plugins.compose)

    // Compose Compiler plugin (required since Kotlin 2.0)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    // Project "desktop" depends on other projects
    implementation(project(":utils"))
    implementation(project(":shared"))

    // Compose Desktop - for building desktop UI applications
    implementation(libs.compose.desktop)
    implementation(libs.bundles.composeDesktop)

    // Skiko native library for Windows
    implementation("org.jetbrains.skiko:skiko-awt-runtime-windows-x64:0.8.15")

    // Coroutines support
    implementation(libs.kotlinx.coroutines.core)

    // Ktor client for making HTTP requests
    implementation(libs.bundles.ktorClient)
    implementation(libs.ktor.client.okhttp)
}

// Configure Compose Desktop
compose.desktop {
    application {
        mainClass = "org.example.desktop.MainKt"

        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg, org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi, org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb)
            packageName = "ComposeDesktopApp"
            packageVersion = "1.0.0"
        }

        // Configure JVM args for Skiko native library loading on Windows
        jvmArgs.add("-Dskiko.native.library=true")
    }
}
