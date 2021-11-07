import org.gradle.kotlin.dsl.kotlin

object Libs {

    object AutoService {
        private const val version = "1.0"
        const val annotation = "com.google.auto.service:auto-service-annotations:$version"
        const val processor = "com.google.auto.service:auto-service:$version"
    }

    object Kotlin {
        private const val version = "1.5.31"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object Ksp {
        internal const val version = "1.5.31-1.0.0"
        const val api = "com.google.devtools.ksp:symbol-processing-api:$version"
    }

    object MavenPublish {
        private const val version = "0.15.1"
        const val gradlePlugin = "com.vanniktech:gradle-maven-publish-plugin:$version"
    }
}

private const val mavenPublish = "com.vanniktech.maven.publish"
private const val ksp = "com.google.devtools.ksp"

fun org.gradle.plugin.use.PluginDependenciesSpec.javaLibrary(): org.gradle.plugin.use.PluginDependencySpec =
    id("java-library")

fun org.gradle.plugin.use.PluginDependenciesSpec.kotlinJVM(): org.gradle.plugin.use.PluginDependencySpec =
    kotlin("jvm")

fun org.gradle.plugin.use.PluginDependenciesSpec.kotlinKapt(): org.gradle.plugin.use.PluginDependencySpec =
    kotlin("kapt")

fun org.gradle.plugin.use.PluginDependenciesSpec.kotlinKsp(): org.gradle.plugin.use.PluginDependencySpec =
    id(ksp).version(Libs.Ksp.version)

fun org.gradle.plugin.use.PluginDependenciesSpec.mavenPublish(): org.gradle.plugin.use.PluginDependencySpec =
    id(mavenPublish)