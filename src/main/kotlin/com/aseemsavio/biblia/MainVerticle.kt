package com.aseemsavio.biblia

import com.aseemsavio.biblia.data.theBible
import com.aseemsavio.biblia.routes.BibliaRoutes
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await

class MainVerticle : CoroutineVerticle() {

  override suspend fun start() {
    val router = Router.router(vertx)
    val bible = theBible()

    BibliaRoutes().configureRoutes(router)

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080)
      .await()

  }
}

suspend fun main() {
  val a = theBible()
  println(a.size)
}
