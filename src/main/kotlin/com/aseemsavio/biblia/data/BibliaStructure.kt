package com.aseemsavio.biblia.data

suspend fun bibleMap(): BibleMap = verses().data()

private suspend fun verses(): List<Verse> = bible().chapters().verses()

private fun List<Verse>.data(): BibleMap {
  return this.groupBy { Testament(it.testament.value) }
    .mapValues { (_, testament) ->
      testament.groupBy { BibleBookName(it.book.value) }
        .mapValues { (_, book) ->
          book.groupBy { BibleChapter(it.chapter) }
        }
    }
}

typealias BibleMap = Map<Testament, Map<BibleBookName, Map<BibleChapter, List<Verse>>>>
