tasks.register("prepareKotlinBuildScriptModel") {}

plugins {
  kotlin("jvm")
  application
  id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(project(":domain"))
  // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
  // Kotlin JSON serialization
  implementation("org.jetbrains.kotlin:kotlin-serialization:1.5.30")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
}
