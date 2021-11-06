tasks.register("prepareKotlinBuildScriptModel") {}

plugins {
  kotlin("jvm")
  application
}

val vertxVersion = "4.1.4"

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(project(":core"))
  implementation(project(":rest"))
  implementation(project(":domain"))


  /* Vert.x */
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  implementation("io.vertx:vertx-web-client")
  implementation("io.vertx:vertx-web")
  implementation("io.vertx:vertx-lang-kotlin")

  /* Coroutines */
  implementation("io.vertx:vertx-lang-kotlin-coroutines")
  // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

  /*// Kotlin JSON serialization
  implementation("org.jetbrains.kotlin:kotlin-serialization:1.5.30")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")*/
}
