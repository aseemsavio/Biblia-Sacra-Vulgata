package com.aseemsavio.biblia.data.service.api

import com.aseemsavio.biblia.data.BibleBookName
import com.aseemsavio.biblia.data.BibleChapter
import com.aseemsavio.biblia.data.Testament
import com.aseemsavio.biblia.data.database.extensions.TotalChapters
import com.aseemsavio.biblia.data.database.extensions.TotalVerses
import com.aseemsavio.biblia.data.database.extensions.VerseNumber
import com.aseemsavio.biblia.data.preparation.*

interface BibiliaService : TestamentService, BookService, ChaptersService, VersesService

interface TestamentService {
  fun getTestamentNames(version: Version): Set<String>
  fun getTestaments(version: Version, testamentName: String?): List<JsonTestament>
}

interface BookService {
  fun getBookNames(version: Version, testamentName: String? = null): List<BookNamesItem>
  fun getBook(version: Version, bookName: String): JsonBook?
}

interface ChaptersService {
  fun getTotalChapters(version: Version, testament: Testament, book: BibleBookName): TotalChapters
  fun getChapter(version: Version, testament: Testament, book: BibleBookName, chapter: BibleChapter): List<JsonVerse>
}

interface VersesService {
  fun getTotalVerses(version: Version, testament: Testament, book: BibleBookName, chapter: BibleChapter): TotalVerses
  fun getVerse(version: Version, testament: Testament, book: BibleBookName, chapter: BibleChapter, verse: VerseNumber): JsonVerse?
  fun getVerses(
    version: Version,
    testament: Testament,
    book: BibleBookName,
    chapter: BibleChapter,
    from: VerseNumber,
    to: VerseNumber
  ): List<JsonVerse>
}
