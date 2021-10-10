package com.aseemsavio.biblia.data.service.api

import com.aseemsavio.biblia.data.BookNamesItem
import com.aseemsavio.biblia.data.JsonBook
import com.aseemsavio.biblia.data.JsonTestament

interface BibiliaService : TestamentService, BookService

interface TestamentService {
  fun getTestamentNames(): Set<String>
  fun getTestaments(testamentName: String?): JsonTestamentList
}

interface BookService {
  fun getBookNames(testamentName: String?): BookNamesList
  fun getBook(bookName: String): JsonBook?
}

typealias JsonTestamentList = List<JsonTestament>
typealias BookNamesList = List<BookNamesItem>
