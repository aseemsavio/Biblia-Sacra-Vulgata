package com.aseemsavio.bibilia.utils.config

import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.json.JsonObject
import io.vertx.config.ConfigRetriever.create
import io.vertx.core.Vertx
import java.lang.Exception

suspend fun config(vertx: Vertx) {
    val mutableConfig = MutableConfig()
    bibiliaJsonConfig(ConfigRetrieverOptions(), ConfigStoreOptions(), "file", "app.json", vertx, mutableConfig)
}

private suspend fun bibiliaJsonConfig(
    retrieverOptions: ConfigRetrieverOptions,
    fileConfig: ConfigStoreOptions,
    type: String,
    file: String,
    vertx: Vertx,
    config: MutableConfig
) {
    fileConfig.setType(type).setFormat("json").config = JsonObject().put("path", "$file")
    retrieverOptions.addStore(fileConfig)
    val retriever = create(vertx, retrieverOptions)
    retriever.getConfig {
        if (it.succeeded()) {
            val result: JsonObject = it.result()
            with(result) {
                val port = int { "port" }
                val developers = array { "developers" }.stringList()

                val versions = array { "versions" }.stringList { "version" }
                val links = array { "versions" }.stringList { "link" }

                println("port: $port")
                println("dev: $developers")
                println("versions: $versions")
                println("links: $links")
            }
            println("Result: ${result.encodePrettily()}")
        } else throw Exception("Could not load config from $file.")
    }
}

internal data class MutableConfig(
    var port: Int = 0,
    var developers: List<String> = listOf()
)
