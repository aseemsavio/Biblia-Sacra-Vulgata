package com.aseemsavio.biblia.data.preparation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

suspend fun bible(url: URL): BibleJson = Json { ignoreUnknownKeys = true }.decodeFromString(getRawBibleJson(url))

val VULGATE_URL = URL("https://raw.githubusercontent.com/aseemsavio/Latin-Vulgate-English-Translation-JSON/master/Generated-JSON/Latin-Vulgate-English-Translation-Study-Bible/bible.json")

private suspend fun getRawBibleJson(url: URL): String =
  withContext(Dispatchers.IO) { url.readText() }

@Serializable
data class JsonVerse(
  val chapter: Int,
  val verse: Int,
  val textEn: String,
  val textLa: String,
  val notes: String? = null
)

@Serializable
data class JsonChapter(
  val chapter: Int,
  val verses: List<JsonVerse>
)

@Serializable
data class JsonBook(
  val bookNumber: Int,
  val book: String,
  val testament: String,
  val chapters: List<JsonChapter>
)

@Serializable
data class JsonTestament(
  val testament: String,
  val books: List<JsonBook>
)

@Serializable
data class BookNamesItem(
  val testament: String,
  val bookNames: Set<String>
)

@JvmInline
value class Version(val value: String)

data class VersionInfo(
  val version: Version,
  val url: URL
)

typealias BibleJson = List<JsonBook>
