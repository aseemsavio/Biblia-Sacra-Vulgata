package com.aseemsavio.biblia

import com.aseemsavio.biblia.data.bibleMap
import com.aseemsavio.biblia.data.database.api.BibiliaMapDatabase.Companion.initialiseMapDB
import com.aseemsavio.biblia.routes.BibiliaRoutes
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await

class MainVerticle : CoroutineVerticle() {

  override suspend fun start() {
    val router = Router.router(vertx)
    val database = initialiseMapDB(bibleMap())

    BibiliaRoutes().configureRoutes(router)

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080)
      .await()
  }
}

suspend fun main() {
  val a = bibleMap()
  println(a)
}
