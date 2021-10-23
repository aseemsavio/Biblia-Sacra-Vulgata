package com.aseemsavio.biblia.data.service

import com.aseemsavio.biblia.data.BibleBookName
import com.aseemsavio.biblia.data.BibleChapter
import com.aseemsavio.biblia.data.Testament
import com.aseemsavio.biblia.data.database.api.BibiliaDatabase
import com.aseemsavio.biblia.data.database.api.TotalChapters
import com.aseemsavio.biblia.data.database.api.TotalVerses
import com.aseemsavio.biblia.data.database.api.VerseNumber
import com.aseemsavio.biblia.data.preparation.BookNamesItem
import com.aseemsavio.biblia.data.preparation.JsonBook
import com.aseemsavio.biblia.data.preparation.JsonTestament
import com.aseemsavio.biblia.data.preparation.JsonVerse
import com.aseemsavio.biblia.data.service.api.BibiliaService

class BibiliaMapService(private val database: BibiliaDatabase) : BibiliaService {

  override fun getTestamentNames(): Set<String> = database.getTestamentNames().map { it.value }.toSet()

  override fun getTestaments(testamentName: String?): List<JsonTestament> {
    TODO("Not yet implemented")
  }

  override fun getBookNames(testamentName: String?): List<BookNamesItem> {
    fun getBooks(t: String) = database.getBookNames(Testament(t)).map { testament ->
      testament.value?.map { it.value }?.let { BookNamesItem(testament.key.value, it.toSet()) }
    }.filterNotNull()

    return if (testamentName == null) getTestamentNames().map { t -> getBooks(t) }.flatten()
    else getBooks(testamentName)
  }

  override fun getBook(bookName: String): JsonBook? {
    TODO("Not yet implemented")
  }

  override fun getTotalChapters(testament: Testament, book: BibleBookName): TotalChapters {
    TODO("Not yet implemented")
  }

  override fun getChapter(testament: Testament, book: BibleBookName, chapter: BibleChapter): List<JsonVerse> {
    TODO("Not yet implemented")
  }

  override fun getTotalVerses(testament: Testament, book: BibleBookName, chapter: BibleChapter): TotalVerses {
    TODO("Not yet implemented")
  }

  override fun getVerse(
    testament: Testament,
    book: BibleBookName,
    chapter: BibleChapter,
    verse: VerseNumber
  ): JsonVerse? {
    TODO("Not yet implemented")
  }

  override fun getVerses(
    testament: Testament,
    book: BibleBookName,
    chapter: BibleChapter,
    from: VerseNumber,
    to: VerseNumber
  ): List<JsonVerse>? {
    TODO("Not yet implemented")
  }
}
