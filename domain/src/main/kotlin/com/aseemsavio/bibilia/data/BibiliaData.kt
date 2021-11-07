package com.aseemsavio.bibilia.data

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
  val text: String,
  val notes: String?
)

val String.t get() = Testament(this)
val String.b get() = BibleBookName(this)
val Int.c get() = BibleChapter(this)


typealias BibiliaMap = TestamentMap
typealias TestamentMap = Map<Testament, BooksMap>
typealias BooksMap = Map<BibleBookName, ChaptersMap>
typealias ChaptersMap = Map<BibleChapter, VersesList>
typealias VersesList = List<Verse>

typealias BookNames = Map<Testament, Set<BibleBookName>?>
typealias VerseNumber = Int
typealias TotalChapters = Int
typealias TotalVerses = Int
typealias TestamentNames = Set<Testament>
