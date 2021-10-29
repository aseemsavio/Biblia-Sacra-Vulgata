package com.aseemsavio.biblia.routes

import com.aseemsavio.biblia.data.BibleBookName
import com.aseemsavio.biblia.data.BibleChapter
import com.aseemsavio.biblia.data.Testament
import com.aseemsavio.biblia.data.preparation.Version
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

  private val prefix = "/api/v1"
  private val defaultVersion = Version("Vulgate")

  suspend fun configureRoutes(router: Router) {
    with(router) {
      `health check route`(this)
      `testament routes`(this)
      `get book names in testament route`(this)
      `get all book names route`(this)
      `chapter count route`(this)
      `get chapter route`(this)
      `verse count route`(this)
      `get verse route`(this)
      `get verses route`(this)
    }
  }

  private suspend fun `health check route`(router: Router) {
    router.get("/").serve {
      with(it) {
        ok { text { "Gloria Patri, et filio, et spiritui sancto in saecula saeculorum! Biblia Sacra Vulgata is UP!!" } }
      }
    }
  }

  private suspend fun `testament routes`(router: Router) {
    val res = service.getTestamentNames(defaultVersion)
    router.get("$prefix/testaments").serve { with(it) { ok { json { res } } } }
  }

  private suspend fun `get book names in testament route`(router: Router) {
    router.get("$prefix/testament/:testament/books").serve {
      with(it) {
        val testament = pp { "testament" }
        val res = service.getBookNames(defaultVersion, testament)
        ok { json { res } }
      }
    }
  }

  private suspend fun `get all book names route`(router: Router) {
    router.get("$prefix/books").serve {
      val res = service.getBookNames(defaultVersion)
      with(it) { ok { json { res } } }
    }
  }

  private suspend fun `chapter count route`(router: Router) {
    router.get("$prefix/testament/:testament/book/:book/chapterCount").serve {
      with(it) {
        val testament = pp { "testament" }
        val book = pp { "book" }
        val res = service.getTotalChapters(defaultVersion, Testament(testament), BibleBookName(book))
        ok { text { res } }
      }
    }
  }

  private suspend fun `get chapter route`(router: Router) {
    router.get("$prefix/testament/:testament/book/:book/chapter/:chapter").serve {
      with(it) {
        val testament = pp { "testament" }
        val book = pp { "book" }
        val chapter = ppAsInt { "chapter" }
        val res = service.getChapter(defaultVersion, Testament(testament), BibleBookName(book), BibleChapter(chapter))
        ok { json { res } }
      }
    }
  }

  private suspend fun `verse count route`(router: Router) {
    router.get("$prefix/testament/:testament/book/:book/chapter/:chapter/verseCount").serve {
      with(it) {
        val testament = pp { "testament" }
        val book = pp { "book" }
        val chapter = ppAsInt { "chapter" }
        val res = service.getTotalVerses(defaultVersion, Testament(testament), BibleBookName(book), BibleChapter(chapter))
        ok { text { res } }
      }
    }
  }

  private suspend fun `get verse route`(router: Router) {
    router.get("$prefix/testament/:testament/book/:book/chapter/:chapter/verse/:verse").serve {
      with(it) {
        val testament = pp { "testament" }
        val book = pp { "book" }
        val chapter = ppAsInt { "chapter" }
        val verse = ppAsInt { "verse" }
        val res = service.getVerse(defaultVersion, Testament(testament), BibleBookName(book), BibleChapter(chapter), verse)
        ok { json { res } }
      }
    }
  }

  private suspend fun `get verses route`(router: Router) {
    router.get("$prefix/testament/:testament/book/:book/chapter/:chapter/verses/from/:from/to/:to").serve {
      with(it) {
        val testament = pp { "testament" }
        val book = pp { "book" }
        val chapter = ppAsInt { "chapter" }
        val from = ppAsInt { "from" }
        val to = ppAsInt { "to" }
        val res = service.getVerses(defaultVersion, Testament(testament), BibleBookName(book), BibleChapter(chapter), from, to)
        ok { json { res } }
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
