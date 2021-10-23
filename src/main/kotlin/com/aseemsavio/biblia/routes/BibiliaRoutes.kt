package com.aseemsavio.biblia.routes

import com.aseemsavio.biblia.data.BibleBookName
import com.aseemsavio.biblia.data.BibleChapter
import com.aseemsavio.biblia.data.Testament
import com.aseemsavio.biblia.data.service.api.BibiliaService
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext
import kotlinx.serialization.encodeToString

/**
 * @author Aseem Savio
 * @since v1
 *
 * This is where all the route configuration should go.
 */
class BibiliaRoutes(
  private val service: BibiliaService,
  private val json: Json = Json {
    encodeDefaults = false
  } /* encodeDefaults will omit values with null from the JSON */
) : CoroutineScope {

  suspend fun configureRoutes(router: Router) {
    with(router) {
      `configure health check route`(this)
      configureTestamentRoutes(this)
      configureGetBookNamesInTestamentRoute(this)
      configureGetAllBookNamesRoute(this)
      configureGetTotalChaptersRoute(this)
      configureGetChaptersRoute(this)
    }
  }

  private suspend fun `configure health check route`(router: Router) {
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

  private suspend fun configureGetBookNamesInTestamentRoute(router: Router) {
    router.get("/books/testament/:testament").handle {
      val testament = it.pathParam("testament")
      val books = json.encodeToString(service.getBookNames(testament))
      it.request()
        .response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(books)
    }
  }

  private suspend fun configureGetAllBookNamesRoute(router: Router) {
    router.get("/books").handle {
      val books = json.encodeToString(service.getBookNames())
      it.request()
        .response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(books)
    }
  }

  private suspend fun configureGetTotalChaptersRoute(router: Router) {
    router.get("/testament/:testament/book/:book/chapterCount").handle {
      val testament = it.pathParam("testament")
      val book = it.pathParam("book")
      it.request()
        .response()
        .putHeader("content-type", "text/plain")
        .setStatusCode(200)
        .end(service.getTotalChapters(Testament(testament), BibleBookName(book)).toString())
    }
  }

  private suspend fun configureGetChaptersRoute(router: Router) {
    router.get("/testament/:testament/book/:book/chapter/:chapter").handle {
      val testament = it.pathParam("testament")
      val book = it.pathParam("book")
      val chapter = it.pathParam("chapter").toInt()
      it.request()
        .response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(json.encodeToString(service.getChapter(Testament(testament), BibleBookName(book), BibleChapter(chapter))))
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
