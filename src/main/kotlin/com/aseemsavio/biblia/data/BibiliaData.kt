package com.aseemsavio.biblia.data

@JvmInline
value class Testament(val value: String)

@JvmInline
value class BibleBookName(val value: String)

@JvmInline
value class BibleChapter(val value: Int)

data class Books(
  val books: Map<BibleBookName, Book>
)

data class Book(
  val book: Map<BibleChapter, Chapter>
)

data class Chapter(
  val book: BibleBookName,
  val verses: List<Verse>
)

data class Verse(
  val book: BibleBookName,
  val testament: Testament,
  val chapter: Int,
  val verse: Int,
  val textEn: String,
  val textLa: String,
  val notes: String?
)
