plugins {
    kotlinJVM()
    kotlinKsp()
    mavenPublish()
}

dependencies {
    implementation(project(":gradle-plugin:annotations"))
    ksp(project(":gradle-plugin:processor"))
}