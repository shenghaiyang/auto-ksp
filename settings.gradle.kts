dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}

rootProject.name = "auto-ksp"

include(":gradle-plugin:annotations")
include(":gradle-plugin:processor")

include(":sample")