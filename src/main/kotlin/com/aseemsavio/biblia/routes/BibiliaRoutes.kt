package com.aseemsavio.biblia.routes

import com.aseemsavio.biblia.data.service.api.BibiliaService
import io.vertx.core.json.JsonObject
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
class BibiliaRoutes(private val service: BibiliaService) : CoroutineScope {

  suspend fun configureRoutes(router: Router) {
    with(router) {
      configureHealthCheckRoute(this)
      configureTestamentRoutes(this)
      configureGetBookNamesRoute(this)
    }
  }

  private suspend fun configureHealthCheckRoute(router: Router) {
    router.get("/").handle {
      it.request()
        .response()
        .putHeader("content-type", "text/plain")
        .setStatusCode(200)
        .end("Gloria Patri, et filio, et spiritui sancto in saecula saeculorum! Biblia Sacra Vulgata is UP!!")
    }
  }

  private suspend fun configureTestamentRoutes(router: Router) {
    router.get("/testaments").handle {
      it.request()
        .response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(service.getTestamentNames().toString())
    }
  }

  private suspend fun configureGetBookNamesRoute(router: Router) {
    router.get("/books").handle {
      val books = JsonObject.mapFrom(service.getBookNames("OT")).toString()
      it.request()
        .response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(books)
    }
  }

  override val coroutineContext: CoroutineContext
    get() = Dispatchers.IO

  /**
   * An extension method for simplifying coroutines usage with Vert.x Web routers
   */
  private suspend fun Route.handle(fn: suspend (RoutingContext) -> Unit) {
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
