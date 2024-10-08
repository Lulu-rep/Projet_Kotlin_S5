/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.4/userguide/building_java_projects.html in the Gradle documentation.
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.9.10"
    id ("org.openjfx.javafxplugin") version "0.1.0"
    id ("application")

}

javafx {
    version = "21.0.1"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.web")
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

sourceSets {
    create("integration") {
        kotlin {
            compileClasspath += main.get().output + configurations["testRuntimeClasspath"]
            runtimeClasspath += output + compileClasspath + test.get().runtimeClasspath
        }
    }
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:32.1.1-jre")

    //Fuel API
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-gson:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-coroutines:2.3.1")

    //gson google
    implementation("com.google.code.gson:gson:2.10.1")

    //kotlin coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    //LOG4J
    implementation("org.apache.logging.log4j:log4j-core:2.22.0")
    implementation("org.apache.logging.log4j:log4j-api:2.22.0")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.3.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.10")

    // JavaFX
    implementation("org.openjfx:javafx-base:21.0.1")
    implementation("org.openjfx:javafx-controls:21.0.1")
    implementation("org.openjfx:javafx-web:21.0.1")
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest("1.9.10")
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("org.isen.papernews.AppKt")
}
