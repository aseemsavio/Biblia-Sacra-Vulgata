package com.aseemsavio.biblia.routes

import com.aseemsavio.biblia.data.BibleBookName
import com.aseemsavio.biblia.data.BibleChapter
import com.aseemsavio.biblia.data.Testament
import com.aseemsavio.biblia.data.service.api.BibiliaService
import com.aseemsavio.biblia.routes.dsl.ok
import com.aseemsavio.biblia.routes.dsl.pp
import com.aseemsavio.biblia.routes.dsl.ppAsInt
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
class BibiliaRoutes(
  private val service: BibiliaService
) : CoroutineScope {

  private val PREFIX = "/api/v1"

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
    router.get("/").serve {
      with(it) {
        ok { text { "Gloria Patri, et filio, et spiritui sancto in saecula saeculorum! Biblia Sacra Vulgata is UP!!" } }
      }
    }
  }

  private suspend fun `configure testament routes`(router: Router) {
    router.get("$PREFIX/testaments").serve { with(it) { ok { json { service.getTestamentNames() } } } }
  }

  private suspend fun `configure get book names in testament route`(router: Router) {
    router.get("$PREFIX/testament/:testament/books").serve {
      with(it) {
        val testament = pp { "testament" }
        ok { json { service.getBookNames(testament) } }
      }
    }
  }

  private suspend fun `configure get all book names route`(router: Router) {
    router.get("$PREFIX/books").serve {
      with(it) { ok { json { service.getBookNames() } } }
    }
  }

  private suspend fun `configure chapter count route`(router: Router) {
    router.get("$PREFIX/testament/:testament/book/:book/chapterCount").serve {
      with(it) {
        val testament = pp { "testament" }
        val book = pp { "book" }
        ok { text { service.getTotalChapters(Testament(testament), BibleBookName(book)) } }
      }
    }
  }

  private suspend fun `configure get chapter route`(router: Router) {
    router.get("$PREFIX/testament/:testament/book/:book/chapter/:chapter").serve {
      with(it) {
        val testament = pp { "testament" }
        val book = pp { "book" }
        val chapter = ppAsInt { "chapter" }
        ok { json { service.getChapter(Testament(testament), BibleBookName(book), BibleChapter(chapter)) } }
      }
    }
  }

  private suspend fun `configure verse count route`(router: Router) {
    router.get("$PREFIX/testament/:testament/book/:book/chapter/:chapter/verseCount").serve {
      with(it) {
        val testament = pp { "testament" }
        val book = pp { "book" }
        val chapter = ppAsInt { "chapter" }
        ok { text { service.getTotalVerses(Testament(testament), BibleBookName(book), BibleChapter(chapter)) } }
      }
    }
  }

  private suspend fun `configure get verse route`(router: Router) {
    router.get("$PREFIX/testament/:testament/book/:book/chapter/:chapter/verse/:verse").serve {
      with(it) {
        val testament = pp { "testament" }
        val book = pp { "book" }
        val chapter = ppAsInt { "chapter" }
        val verse = ppAsInt { "verse" }
        ok { json { service.getVerse(Testament(testament), BibleBookName(book), BibleChapter(chapter), verse) } }
      }
    }
  }

  private suspend fun `configure get verses route`(router: Router) {
    router.get("$PREFIX/testament/:testament/book/:book/chapter/:chapter/verses/from/:from/to/:to").serve {
      with(it) {
        val testament = pp { "testament" }
        val book = pp { "book" }
        val chapter = ppAsInt { "chapter" }
        val from = ppAsInt { "from" }
        val to = ppAsInt { "to" }
        ok { json { service.getVerses(Testament(testament), BibleBookName(book), BibleChapter(chapter), from, to) } }
      }
    }
  }

  override val coroutineContext: CoroutineContext
    get() = Dispatchers.IO

  /**
   * An extension method for simplifying coroutines usage with Vert.x Web routers
   */
  private suspend fun Route.serve(fn: suspend (RoutingContext) -> Unit) {
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
