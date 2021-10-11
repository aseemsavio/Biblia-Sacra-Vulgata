package com.aseemsavio.biblia

import com.aseemsavio.biblia.data.preparation.bible
import com.aseemsavio.biblia.data.database.api.MapDatabase
import com.aseemsavio.biblia.data.database.api.initiateDatabase
import com.aseemsavio.biblia.routes.BibiliaRoutes
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await

class MainVerticle : CoroutineVerticle() {
  override suspend fun start() {
    val router = Router.router(vertx)
    val database = bible().initiateDatabase(MapDatabase)
    BibiliaRoutes().configureRoutes(router)
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080)
      .await()
  }
}
