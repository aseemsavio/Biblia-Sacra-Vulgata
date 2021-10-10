package com.aseemsavio.biblia.data.database.api

import com.aseemsavio.biblia.data.*

/**
 * Map based Database Implementation for Bibilia Sacra Vulgata
 */
class BibiliaMapDatabase(private val bible: BibiliaMap) : BibiliaDatabase {

  override fun getTestamentNames(): TestamentNames = bible.keys

  override fun getTestament(testament: Testament): BooksMap =
    bible[testament] ?: emptyMap()

  override fun getBookNames(testament: Testament?): BookNames =
    if (testament == null) getTestamentNames().associateWith { bible[it]?.keys } else
      when (testament) {
        Testament("OT") -> setOf(Testament("OT")).associateWith { bible[it]?.keys }
        Testament("NT") -> setOf(Testament("NT")).associateWith { bible[it]?.keys }
        else -> mapOf()
      }


  override fun getBook(testament: Testament, book: BibleBookName): ChaptersMap = bible[testament]?.get(book) ?: mapOf()

  override fun getTotalChapters(testament: Testament, book: BibleBookName): TotalChapters =
    bible[testament]?.get(book)?.size ?: 0

  override fun getChapter(testament: Testament, book: BibleBookName, chapter: BibleChapter): VersesList =
    bible[testament]?.get(book)?.get(chapter) ?: emptyList()

  override fun getTotalVerses(testament: Testament, book: BibleBookName, chapter: BibleChapter): TotalVerses =
    bible[testament]?.get(book)?.get(chapter)?.size ?: 0

  override fun getVerse(testament: Testament, book: BibleBookName, chapter: BibleChapter, verse: VerseNumber): Verse? =
    bible[testament]?.get(book)?.get(chapter)?.first { it.verse == verse }

  override fun getVerses(
    testament: Testament,
    book: BibleBookName,
    chapter: BibleChapter,
    from: VerseNumber,
    to: VerseNumber
  ): VersesList? = bible[testament]?.get(book)?.get(chapter)?.filter { it.verse in from..to }
}
