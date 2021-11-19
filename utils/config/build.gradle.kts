tasks.register("prepareKotlinBuildScriptModel") {}

plugins {
    kotlin("jvm")
    application
}

val vertxVersion = "4.1.4"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.vertx:vertx-config:4.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    /* https://github.com/lightbend/config */
    implementation("com.typesafe:config:1.4.1")

    implementation(project(":domain"))
}
