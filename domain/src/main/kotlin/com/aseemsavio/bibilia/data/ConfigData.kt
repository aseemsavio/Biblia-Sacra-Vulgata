package com.aseemsavio.bibilia.data

data class Config(
    val port: Int,
    val developers: List<String>,
    val versionInfo: List<ConfigVersion>
)

data class ConfigVersion(
    val name: String,
    val link: String?,
    val fileLocation: String?
)