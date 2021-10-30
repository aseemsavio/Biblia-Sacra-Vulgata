package com.aseemsavio.biblia.utils

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * Does map operation in parallel
 */
suspend fun <A, B> Iterable<A>.pMap(f: suspend (A) -> B): List<B> = coroutineScope {
  map { async { f(it) } }.awaitAll()
}
