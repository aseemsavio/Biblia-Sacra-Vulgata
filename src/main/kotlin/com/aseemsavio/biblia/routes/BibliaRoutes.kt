package com.aseemsavio.biblia.routes

import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * @author Aseem Savio
 * @since v1
 *
 * This is where all the route configuration should go.
 */
class BibliaRoutes : CoroutineScope {

  suspend fun configureRoutes(router: Router) {
    configureHealthCheckRoute(router)
  }

  private suspend fun configureHealthCheckRoute(router: Router) {
    router.get("/").coroutineHandler {
      it.request()
        .response()
        .putHeader("content-type", "text/plain")
        .setStatusCode(200)
        .end("Biblia Sacra Vulgata is UP!!")
    }
  }

  override val coroutineContext: CoroutineContext
    get() = Dispatchers.IO

  /**
   * An extension method for simplifying coroutines usage with Vert.x Web routers
   */
  private suspend fun Route.coroutineHandler(fn: suspend (RoutingContext) -> Unit) {
    handler { context ->
      launch(context.vertx().dispatcher()) {
        try {
          fn(context)
        } catch (e: Exception) {
          e.printStackTrace()
          context.fail(e)
        }
      }
    }
  }

}


