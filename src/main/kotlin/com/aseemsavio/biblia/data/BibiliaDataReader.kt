package com.aseemsavio.biblia.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

suspend fun bible(): List<JsonBook> = Json{ ignoreUnknownKeys = true }.decodeFromString(getRawBibleJson())

private suspend fun getRawBibleJson(): String =
  withContext(Dispatchers.IO) { URL("https://raw.githubusercontent.com/aseemsavio/Latin-Vulgate-English-Translation-JSON/master/Generated-JSON/Latin-Vulgate-English-Translation-Study-Bible/bible.json").readText() }

@Serializable
data class JsonVerse(
  val chapter: Int,
  val verse: Int,
  val textEn: String,
  val textLa: String,
  val notes: String?
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
