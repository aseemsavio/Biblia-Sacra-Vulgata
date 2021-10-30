package com.aseemsavio.biblia

import com.aseemsavio.biblia.data.database.extensions.*
import com.aseemsavio.biblia.data.preparation.Version
import com.aseemsavio.biblia.data.service.BibiliaMapService
import com.aseemsavio.biblia.routes.BibiliaRoutes
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await

class MainVerticle : CoroutineVerticle() {
  override suspend fun start() {
    val router = Router.router(vertx)
    val databases: BibiliaDatabases = initialiseDatabases(versions(), MapDatabase)
    val service = BibiliaMapService(databases)
    BibiliaRoutes(service).configureRoutes(router)
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080)
      .await()
  }
}

suspend fun main() {
  val databases: BibiliaDatabases = initialiseDatabases(versions(), MapDatabase)
  val verse = databases[Version("Vulgate")]
}
