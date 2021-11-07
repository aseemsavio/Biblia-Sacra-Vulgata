package com.aseemsavio.bibilia.data

import kotlinx.serialization.Serializable
import java.net.URL

@Serializable
data class JsonVerse(
  val chapter: Int,
  val verse: Int,
  val text: String,
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

val String.v get() = Version(this)
