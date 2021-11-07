package com.aseemsavio.bibilia.data

import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

suspend fun bible(url: URL): BibleJson = Json { ignoreUnknownKeys = true }.decodeFromString(getRawBibleJson(url))

val VULGATE_URL = URL("https://raw.githubusercontent.com/aseemsavio/Latin-Vulgate-English-Translation-JSON/master/Generated-JSON/Bibles/vulgate.json")
val CPDV_URL = URL("https://raw.githubusercontent.com/aseemsavio/Latin-Vulgate-English-Translation-JSON/master/Generated-JSON/Bibles/cpdv.json")

private suspend fun getRawBibleJson(url: URL): String =
  withContext(Dispatchers.IO) { url.readText() }

/**
 * Does map operation in parallel
 *
 * todo: add this in its own module in the future
 */
suspend fun <A, B> Iterable<A>.pMap(fn: suspend (A) -> B): List<B> = coroutineScope {
  map { async { fn(it) } }.awaitAll()
}
