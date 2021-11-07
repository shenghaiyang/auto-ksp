@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "EXPERIMENTAL_IS_NOT_ENABLED", "unused")

package com.shenghaiyang.auto.ksp.gradle

import com.google.auto.service.AutoService
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile

/**
 * Provide [AutoGradlePluginProcessor]
 */
@AutoService(SymbolProcessorProvider::class)
class AutoGradlePluginProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return AutoGradlePluginProcessor(environment)
    }

}

/**
 * Generate gradle plugin property file for [AutoGradlePlugin]
 */
class AutoGradlePluginProcessor(environment: SymbolProcessorEnvironment) : SymbolProcessor {

    companion object {
        private const val RESOURCES_DIR = "META-INF/gradle-plugins/"
        private const val EXTENSION_NAME_PROPERTIES = "properties"
        private const val NAME_IMPLEMENTATION_CLASS = "implementation-class="
    }

    private val logger = environment.logger
    private val codeGenerator = environment.codeGenerator

    override fun process(resolver: Resolver): List<KSAnnotated> {
        log("AutoGradlePluginProcessor started")
        findPlugins(resolver, this::generateProperties)
        log("AutoGradlePluginProcessor finished")
        return emptyList()
    }

    @OptIn(KspExperimental::class)
    private fun findPlugins(resolver: Resolver, afterFound: (Plugins) -> Unit) {
        val plugins = hashMapOf<String, Pair<String, KSFile>>()
        try {
            resolver.getSymbolsWithAnnotation(AutoGradlePlugin::class.java.name)
                .filterIsInstance<KSClassDeclaration>()
                .forEach { declaration ->
                    declaration.getAnnotationsByType(AutoGradlePlugin::class).forEach { annotation ->
                        val pluginId = annotation.pluginId
                        val className = declaration.qualifiedName?.asString()!!
                        log("found plugin id:$pluginId, className:$className")
                        val file = declaration.containingFile!!
                        plugins[pluginId] = className to file
                    }
                }
            if (plugins.isNotEmpty()) {
                afterFound(plugins)
            }
        } finally {
            plugins.clear()
        }
    }

    private fun generateProperties(plugins: Map<String, Pair<String, KSFile>>) {
        plugins.forEach { (pluginId, value) ->
            log("generate plugin id:$pluginId, className:$value")
            val filePath = RESOURCES_DIR + pluginId
            codeGenerator.createNewFile(
                Dependencies(true, value.second),
                "",
                filePath,
                extensionName = EXTENSION_NAME_PROPERTIES
            ).bufferedWriter().use {
                it.write(NAME_IMPLEMENTATION_CLASS + value.first)
            }
        }

    }

    private fun log(msg: String) {
        logger.warn("----------->$msg")
    }

}

typealias Plugins = Map<String, Pair<String, KSFile>>