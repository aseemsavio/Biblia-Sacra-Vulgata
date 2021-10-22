package com.aseemsavio.biblia.data.service.api

import com.aseemsavio.biblia.data.BibleBookName
import com.aseemsavio.biblia.data.BibleChapter
import com.aseemsavio.biblia.data.Testament
import com.aseemsavio.biblia.data.database.api.TotalChapters
import com.aseemsavio.biblia.data.database.api.TotalVerses
import com.aseemsavio.biblia.data.database.api.VerseNumber
import com.aseemsavio.biblia.data.preparation.*

interface BibiliaService : TestamentService, BookService, ChaptersService, VersesService

interface TestamentService {
  fun getTestamentNames(): Set<String>
  fun getTestaments(testamentName: String?): List<JsonTestament>
}

interface BookService {
  fun getBookNames(testamentName: String?): List<BookNamesItem>
  fun getBook(bookName: String): JsonBook?
}

interface ChaptersService {
  fun getTotalChapters(testament: Testament, book: BibleBookName): TotalChapters
  fun getChapter(testament: Testament, book: BibleBookName, chapter: BibleChapter): List<JsonVerse>
}

interface VersesService {
  fun getTotalVerses(testament: Testament, book: BibleBookName, chapter: BibleChapter): TotalVerses
  fun getVerse(testament: Testament, book: BibleBookName, chapter: BibleChapter, verse: VerseNumber): JsonVerse?
  fun getVerses(
    testament: Testament,
    book: BibleBookName,
    chapter: BibleChapter,
    from: VerseNumber,
    to: VerseNumber
  ): List<JsonVerse>?
}
