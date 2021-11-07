plugins {
    kotlinJVM()
    kotlinKapt()
    mavenPublish()
}

dependencies {
    implementation(project(":gradle-plugin:annotations"))
    implementation(Libs.Ksp.api)

    implementation(Libs.AutoService.annotation)
    kapt(Libs.AutoService.processor)
}