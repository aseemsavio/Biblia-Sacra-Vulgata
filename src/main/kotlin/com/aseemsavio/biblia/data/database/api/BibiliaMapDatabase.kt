package com.aseemsavio.biblia.data.database.api

import com.aseemsavio.biblia.data.*

/**
 * Map based Database Implementation for Bibilia Sacra Vulgata
 */
class BibiliaMapDatabase(private val bible: BibiliaMap) : BibiliaDatabase {

  override fun getTestamentNames(): TestamentNames = bible.keys

  override fun getTestament(testament: Testament): BooksMap =
    bible[testament] ?: emptyMap()

  override fun getBookNames(testament: Testament?): BookNames {
    return if (testament == null) getTestamentNames().associateWith { bible[it]?.keys } else
      when (testament) {
        Testament("OT") -> setOf(Testament("OT")).associateWith { bible[it]?.keys }
        Testament("NT") -> setOf(Testament("NT")).associateWith { bible[it]?.keys }
        else -> mapOf()
      }
  }

  override fun getBook(testament: Testament, book: BibleBookName): ChaptersMap {
    TODO("Not yet implemented")
  }

  override fun getTotalChapters(testament: Testament, book: BibleBookName): TotalChapters {
    TODO("Not yet implemented")
  }

  override fun getChapter(testament: Testament, book: BibleBookName): VersesList {
    TODO("Not yet implemented")
  }

  override fun getTotalVerses(testament: Testament, book: BibleBookName, chapter: BibleChapter): TotalVerses {
    TODO("Not yet implemented")
  }

  override fun getVerse(testament: Testament, book: BibleBookName, chapter: BibleChapter, verse: VerseNumber): Verse? {
    TODO("Not yet implemented")
  }

  override fun getVerses(
    testament: Testament,
    book: BibleBookName,
    chapter: BibleChapter,
    from: VerseNumber,
    to: VerseNumber
  ): VersesList? {
    TODO("Not yet implemented")
  }
}
