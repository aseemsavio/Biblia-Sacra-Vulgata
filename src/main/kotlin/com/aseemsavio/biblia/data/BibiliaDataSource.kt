package com.aseemsavio.biblia.data


fun List<JsonBook>.chapters(): List<Chapter> =
  this.map {
    it.chapters.map { c ->
      Chapter(
        BibleBookName(it.book),
        c.verses.map { v -> Verse(BibleBookName(it.book), c.chapter, v.verse, v.textEn, v.textLa, v.notes) })
    }
  }.flatten()

fun List<Chapter>.verses(): List<Verse> = this.map { it.verses }.flatten()

@JvmInline
value class Testament(val value: String)

@JvmInline
value class BibleBookName(val value: String)

@JvmInline
value class BibleChapter(val value: Int)

data class Bible(
  val bible: Map<Testament, Books>
)

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
  val chapter: Int,
  val verse: Int,
  val textEn: String,
  val textLa: String,
  val notes: String?
)
