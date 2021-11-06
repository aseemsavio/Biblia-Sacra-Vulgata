package com.aseemsavio.bibilia.database.api

import com.aseemsavio.bibilia.data.*

interface BibiliaDatabase : TestamentDatabase, BooksDatabase, ChaptersDatabase, VersesDatabase

interface TestamentDatabase {
  fun getTestamentNames(): TestamentNames
  fun getTestament(testament: Testament): BooksMap
}

interface BooksDatabase {
  fun getBookNames(testament: Testament?): BookNames
  fun getBook(testament: Testament, book: BibleBookName): ChaptersMap
}

interface ChaptersDatabase {
  fun getTotalChapters(testament: Testament, book: BibleBookName): TotalChapters
  fun getChapter(testament: Testament, book: BibleBookName, chapter: BibleChapter): VersesList
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
  ): VersesList
}
