package com.aseemsavio.biblia.data

/**
 * This function returns contents of the Bibilia Sacra Vulgata
 * in an efficient Map Data Structure - [BibiliaMap], most suitable for querying.
 */
suspend fun bibleMap(): BibiliaMap = bible().verses().toMap()

/**
 * The main purpose of this function is to build independent [Verse] objects from multiple JSON objects.
 * This will enable storing them in an efficient data structure - [BibiliaMap], which enables for efficient querying.
 */
private fun List<JsonBook>.verses(): List<Verse> =
  this.map {
    it.chapters.map { c ->
      Chapter(
        BibleBookName(it.book),
        c.verses.map { v ->
          Verse(BibleBookName(it.book), Testament(it.testament), c.chapter, v.verse, v.textEn, v.textLa, v.notes)
        })
    }
  }.flatten()
    .map { it.verses }
    .flatten()


/**
 * This function groups the list of [Verse]s into a multi-layered map in the following order:
 *
 *  1. [Testament]
 *  2. [BibleBookName]
 *  3. [BibleChapter]
 *
 * The final layer, [BibleChapter] will finally have a list of [Verse]s mimicking the original
 * organisation in the Scriptures.
 */
private fun List<Verse>.toMap(): BibiliaMap {
  return this.groupBy { Testament(it.testament.value) }
    .mapValues { (_, testament) ->
      testament.groupBy { BibleBookName(it.book.value) }
        .mapValues { (_, book) ->
          book.groupBy { BibleChapter(it.chapter) }
        }
    }
}

typealias BibiliaMap = TestamentMap
typealias TestamentMap = Map<Testament, BooksMap>
typealias BooksMap = Map<BibleBookName, ChaptersMap>
typealias ChaptersMap = Map<BibleChapter, VersesList>
typealias VersesList = List<Verse>