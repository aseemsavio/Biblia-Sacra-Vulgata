package com.aseemsavio.bibilia.core.api

import com.aseemsavio.bibilia.data.*
import com.aseemsavio.bibilia.database.extensions.BibiliaDatabases

class BibiliaMapService(private val databases: BibiliaDatabases) : BibiliaService {

  override fun getTestamentNames(version: Version): Set<String> =
    databases[version]?.getTestamentNames()?.map { it.value }?.toSet() ?: emptySet()

  override fun getTestaments(version: Version, testamentName: String?): List<JsonTestament> {
    TODO("Not yet implemented")
  }

  override fun getBookNames(version: Version, testamentName: String?): List<BookNamesItem> {
    fun getBooks(t: String) = databases[version]?.getBookNames(Testament(t))?.map { testament ->
      testament.value?.map { it.value }?.let { BookNamesItem(testament.key.value, it.toSet()) }
    }?.filterNotNull() ?: emptyList()

    return if (testamentName == null) getTestamentNames(version).map { t -> getBooks(t) }.flatten()
    else getBooks(testamentName)
  }

  override fun getBook(version: Version, bookName: String): JsonBook? {
    TODO("Not yet implemented")
  }

  override fun getTotalChapters(version: Version, testament: Testament, book: BibleBookName): TotalChapters =
    databases[version]?.getTotalChapters(testament, book) ?: 0

  override fun getChapter(
    version: Version,
    testament: Testament,
    book: BibleBookName,
    chapter: BibleChapter
  ): List<JsonVerse> =
    databases[version]?.getChapter(testament, book, chapter)?.map {
      JsonVerse(it.chapter, it.verse, it.text, it.notes)
    } ?: emptyList()


  override fun getTotalVerses(
    version: Version,
    testament: Testament,
    book: BibleBookName,
    chapter: BibleChapter
  ): TotalVerses =
    databases[version]?.getTotalVerses(testament, book, chapter) ?: 0

  override fun getVerse(
    version: Version,
    testament: Testament,
    book: BibleBookName,
    chapter: BibleChapter,
    verse: VerseNumber
  ): JsonVerse? = databases[version]?.getVerse(testament, book, chapter, verse)?.let {
    JsonVerse(it.chapter, it.verse, it.text, it.notes)
  }

  override fun getVerses(
    version: Version,
    testament: Testament,
    book: BibleBookName,
    chapter: BibleChapter,
    from: VerseNumber,
    to: VerseNumber
  ): List<JsonVerse> = databases[version]?.getVerses(testament, book, chapter, from, to)?.map {
    JsonVerse(it.chapter, it.verse, it.text, it.notes)
  } ?: emptyList()
}
