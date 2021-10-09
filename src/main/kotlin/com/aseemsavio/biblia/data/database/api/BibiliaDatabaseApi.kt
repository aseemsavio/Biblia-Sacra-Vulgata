package com.aseemsavio.biblia.data.database.api

import com.aseemsavio.biblia.data.*

interface BibiliaDatabase : TestamentDatabase, BooksDatabase, ChaptersDatabase, VersesDatabase

interface TestamentDatabase {
  fun getTestamentNames(): TestamentNames
  fun getTestament(testament: Testament): BooksMap
}

interface BooksDatabase {
  fun getBookNames(testament: Testament?): BookNames
  fun getBook(book: BibleBookName): ChaptersMap
}

interface ChaptersDatabase {
  fun getTotalChapters(book: BibleBookName): TotalChapters
  fun getChapter(book: BibleBookName): VersesList
}

interface VersesDatabase {
  fun getTotalVerses(testament: Testament, book: BibleBookName, chapter: BibleChapter): TotalVerses
  fun getVerse(testament: Testament, book: BibleBookName, chapter: BibleChapter, verse: VerseNumber): Verse?
  fun getVerses(
    testament: Testament,
    book: BibleBookName,
    chapter: BibleChapter,
    from: VerseNumber,
    to: VerseNumber
  ): VersesList?
}

typealias VerseNumber = Int
typealias TotalChapters = Int
typealias TotalVerses = Int
typealias TestamentNames = List<Testament>

data class BookNames(val names: List<TestamentBookNames>)
data class TestamentBookNames(val testament: Testament, val bookNames: List<BibleBookName>)
