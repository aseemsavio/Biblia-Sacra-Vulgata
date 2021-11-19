package com.aseemsavio.bibilia.server

import com.aseemsavio.bibilia.database.extensions.initialiseDatabases
import com.aseemsavio.bibilia.database.extensions.versions
import com.aseemsavio.bibilia.core.api.BibiliaMapService
import com.aseemsavio.bibilia.database.extensions.db
import com.aseemsavio.bibilia.rest.endpoint.routes.BibiliaRoutes
import com.aseemsavio.bibilia.utils.config.lightbend.config

import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.await

class MainVerticle : CoroutineVerticle() {
    override suspend fun start() {
        val config = config()
        val router = Router.router(vertx)
        val databases = initialiseDatabases(versions(config), config.database.db)
        val service = BibiliaMapService(databases)
        BibiliaRoutes(service).configureRoutes(router)
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(config.port)
            .await()
    }
}
