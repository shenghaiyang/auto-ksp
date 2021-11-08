buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(Libs.Kotlin.gradlePlugin)
        classpath(Libs.MavenPublish.gradlePlugin)
    }
}

subprojects {
    tasks.withType(JavaCompile::class) {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
    }
    plugins.withType(com.vanniktech.maven.publish.MavenPublishPlugin::class.java) {
        val extension = extensions.getByType(com.vanniktech.maven.publish.MavenPublishPluginExtension::class)
        extension.sonatypeHost = com.vanniktech.maven.publish.SonatypeHost.S01

        configure<PublishingExtension> {
            repositories {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/shenghaiyang/auto-ksp")
                    credentials {
                        username = project.findProperty("gpr.user") as String? ?: project.property("GITHUB_USERNAME").toString()
                        password = project.findProperty("gpr.key") as String? ?: project.property("GITHUB_TOKEN").toString()
                    }
                }
            }
            publications {

                register<MavenPublication>("gpr") {
                    from(components["java"])
                }
            }
        }

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}