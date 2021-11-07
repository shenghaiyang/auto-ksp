package com.shenghaiyang.auto.ksp.gradle

/**
 * Auto generate gradle plugin property file.
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class AutoGradlePlugin(val pluginId: String)