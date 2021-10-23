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
      `configure testament routes`(this)
      `configure get book names in testament route`(this)
      `configure get all book names route`(this)
      `configure chapter count route`(this)
      `configure get chapter route`(this)
      `configure verse count route`(this)
      `configure get verse route`(this)
      `configure get verses route`(this)
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

  private suspend fun `configure testament routes`(router: Router) {
    router.get("/testaments").handle {
      it.request()
        .response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(service.getTestamentNames().toString())
    }
  }

  private suspend fun `configure get book names in testament route`(router: Router) {
    router.get("/testament/:testament/books").handle {
      val testament = it.pathParam("testament")
      val books = json.encodeToString(service.getBookNames(testament))
      it.request()
        .response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(books)
    }
  }

  private suspend fun `configure get all book names route`(router: Router) {
    router.get("/books").handle {
      val books = json.encodeToString(service.getBookNames())
      it.request()
        .response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(books)
    }
  }

  private suspend fun `configure chapter count route`(router: Router) {
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

  private suspend fun `configure get chapter route`(router: Router) {
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

  private suspend fun `configure verse count route`(router: Router) {
    router.get("/testament/:testament/book/:book/chapter/:chapter/verseCount").handle {
      val testament = it.pathParam("testament")
      val book = it.pathParam("book")
      val chapter = it.pathParam("chapter").toInt()
      it.request()
        .response()
        .putHeader("content-type", "text/plain")
        .setStatusCode(200)
        .end(service.getTotalVerses(Testament(testament), BibleBookName(book), BibleChapter(chapter)).toString())
    }
  }

  private suspend fun `configure get verse route`(router: Router) {
    router.get("/testament/:testament/book/:book/chapter/:chapter/verse/:verse").handle {
      val testament = it.pathParam("testament")
      val book = it.pathParam("book")
      val chapter = it.pathParam("chapter").toInt()
      val verse = it.pathParam("verse").toInt()
      it.request()
        .response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(
          json.encodeToString(
            service.getVerse(
              Testament(testament),
              BibleBookName(book),
              BibleChapter(chapter),
              verse
            )
          )
        )
    }
  }

  private suspend fun `configure get verses route`(router: Router) {
    router.get("/testament/:testament/book/:book/chapter/:chapter/verses/from/:from/to/:to").handle {
      val testament = it.pathParam("testament")
      val book = it.pathParam("book")
      val chapter = it.pathParam("chapter").toInt()
      val from = it.pathParam("from").toInt()
      val to = it.pathParam("to").toInt()
      it.request()
        .response()
        .putHeader("content-type", "application/json")
        .setStatusCode(200)
        .end(
          json.encodeToString(
            service.getVerses(
              Testament(testament),
              BibleBookName(book),
              BibleChapter(chapter),
              from,
              to
            )
          )
        )
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
