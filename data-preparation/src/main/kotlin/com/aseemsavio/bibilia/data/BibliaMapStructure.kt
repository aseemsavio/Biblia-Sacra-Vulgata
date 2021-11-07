package com.aseemsavio.bibilia.data

/**
 * The main purpose of this function is to build independent [Verse] objects from multiple JSON objects.
 * This will enable storing them in an efficient data structure - [BibiliaMap], which enables for efficient querying.
 */
fun BibleJson.verses(): List<Verse> =
  this.map {
    it.chapters.map { c ->
      Chapter(
        BibleBookName(it.book),
        c.verses.map { v ->
          Verse(BibleBookName(it.book), Testament(it.testament), c.chapter, v.verse, v.text, v.notes)
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
fun List<Verse>.toMap(): BibiliaMap {
  return this.groupBy { Testament(it.testament.value) }
    .mapValues { (_, testament) ->
      testament.groupBy { BibleBookName(it.book.value) }
        .mapValues { (_, book) ->
          book.groupBy { BibleChapter(it.chapter) }
        }
    }
}
