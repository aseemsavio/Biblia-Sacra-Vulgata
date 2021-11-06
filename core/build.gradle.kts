tasks.register("prepareKotlinBuildScriptModel") {}

plugins {
  kotlin("jvm")
  application
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(project(":database"))
  implementation(project(":domain"))
}
