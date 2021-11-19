package com.aseemsavio.bibilia.utils.config.lightbend

import com.aseemsavio.bibilia.data.ConfigData
import com.aseemsavio.bibilia.data.ConfigVersionData
import com.typesafe.config.ConfigFactory

/**
 * Loads configuration and returns a [Config] object.
 * Config information can be retrieved from it.
 */
private suspend fun loadConfig() = ConfigFactory.load()

suspend fun config(): ConfigData = with(loadConfig()) {
    ConfigData(
        port = int { "port" },
        developers = strings { "developers" },
        database = string { "database" },
        versionInfo = list { "versions" }.map {
            ConfigVersionData(
                it.string { "version" },
                it.string { "link" }
            )
        }
    )
}
