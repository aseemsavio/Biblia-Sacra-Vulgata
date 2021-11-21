package com.aseemsavio.bibilia.rest.endpoint.routes

import com.aseemsavio.bibilia.core.api.BibiliaService
import com.aseemsavio.bibilia.data.b
import com.aseemsavio.bibilia.data.c
import com.aseemsavio.bibilia.data.t
import com.aseemsavio.bibilia.data.v
import com.aseemsavio.vertx.rest.dsl.*
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

    private val default = "Vulgate"

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
                val res =
                    "Gloria Patri, et filio, et spiritui sancto in saecula saeculorum! Biblia Sacra Vulgata is UP!!"
                ok { text { res } }
            }
        }
    }

    private suspend fun `testament routes`(router: Router) {
        router.get("$PREFIX/testaments").serve {
            with(it) {
                val version = qp(default) { "version" }
                val res = service.getTestamentNames(version.v)
                fold(
                    res,
                    { ok { json { res } } },
                    { notFound { } }
                )
            }
        }
    }

    private suspend fun `get book names in testament route`(router: Router) {
        router.get("$PREFIX/testament/:testament/books").serve {
            with(it) {
                val testament = pp { "testament" }
                val version = qp(default) { "version" }
                val res = service.getBookNames(version.v, testament)
                fold(
                    res,
                    { ok { json { res } } },
                    { notFound { } }
                )
            }
        }
    }

    private suspend fun `get all book names route`(router: Router) {
        router.get("$PREFIX/books").serve {
            with(it) {
                val version = qp(default) { "version" }
                val res = service.getBookNames(version.v)
                fold(
                    res,
                    { ok { json { res } } },
                    { notFound { } }
                )
            }
        }
    }

    private suspend fun `chapter count route`(router: Router) {
        router.get("$PREFIX/testament/:testament/book/:book/chapterCount").serve {
            with(it) {
                val testament = pp { "testament" }
                val book = pp { "book" }
                val version = qp(default) { "version" }
                val res = service.getTotalChapters(version.v, testament.t, book.b)
                fold(
                    res,
                    { ok { text { res } } },
                    { notFound { } }
                )
            }
        }
    }

    private suspend fun `get chapter route`(router: Router) {
        router.get("$PREFIX/testament/:testament/book/:book/chapter/:chapter").serve {
            with(it) {
                val testament = pp { "testament" }
                val book = pp { "book" }
                val chapter = ppAsInt { "chapter" }
                val version = qp(default) { "version" }
                val res = service.getChapter(version.v, testament.t, book.b, chapter.c)
                fold(
                    res,
                    { ok { json { res } } },
                    { notFound { } }
                )
            }
        }
    }

    private suspend fun `verse count route`(router: Router) {
        router.get("$PREFIX/testament/:testament/book/:book/chapter/:chapter/verseCount").serve {
            with(it) {
                val testament = pp { "testament" }
                val book = pp { "book" }
                val chapter = ppAsInt { "chapter" }
                val version = qp(default) { "version" }
                val res = service.getTotalVerses(version.v, testament.t, book.b, chapter.c)
                fold(
                    res,
                    { ok { text { res } } },
                    { notFound { } }
                )
            }
        }
    }

    private suspend fun `get verse route`(router: Router) {
        router.get("$PREFIX/testament/:testament/book/:book/chapter/:chapter/verse/:verse").serve {
            with(it) {
                val testament = pp { "testament" }
                val book = pp { "book" }
                val chapter = ppAsInt { "chapter" }
                val verse = ppAsInt { "verse" }
                val version = qp(default) { "version" }
                val res = service.getVerse(version.v, testament.t, book.b, chapter.c, verse)
                fold(
                    res,
                    { ok { json { res } } },
                    { notFound { } }
                )
            }
        }
    }

    private suspend fun `get verses route`(router: Router) {
        router.get("$PREFIX/testament/:testament/book/:book/chapter/:chapter/verses/from/:from/to/:to").serve {
            with(it) {
                val testament = pp { "testament" }
                val book = pp { "book" }
                val chapter = ppAsInt { "chapter" }
                val from = ppAsInt { "from" }
                val to = ppAsInt { "to" }
                val version = qp(default) { "version" }
                val res = service.getVerses(version.v, testament.t, book.b, chapter.c, from, to)
                fold(
                    res,
                    { ok { json { res } } },
                    { notFound { } }
                )
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

private const val PREFIX = "/api/v1"
