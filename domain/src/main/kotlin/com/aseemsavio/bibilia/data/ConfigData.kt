package com.aseemsavio.bibilia.data

data class ConfigData(
    val port: Int,
    val developers: List<String>,
    val database: String,
    val versionInfo: List<ConfigVersionData>
)

data class ConfigVersionData(
    val name: String,
    val link: String,
)
