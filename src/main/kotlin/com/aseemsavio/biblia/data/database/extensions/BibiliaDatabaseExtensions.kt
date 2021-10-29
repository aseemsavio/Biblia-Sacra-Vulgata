package com.aseemsavio.biblia.data.database.extensions

import com.aseemsavio.biblia.data.BibleBookName
import com.aseemsavio.biblia.data.Testament
import com.aseemsavio.biblia.data.database.api.BibiliaDatabase
import com.aseemsavio.biblia.data.database.api.BibiliaMapDatabase
import com.aseemsavio.biblia.data.preparation.*


typealias BookNames = Map<Testament, Set<BibleBookName>?>
typealias VerseNumber = Int
typealias TotalChapters = Int
typealias TotalVerses = Int
typealias TestamentNames = Set<Testament>

sealed class Database
object MapDatabase : Database()

/**
 * Data will always be brought in as a [BibleJson] object.
 * Depending on the type of [Database] requested, this function returns it after
 * injecting it with the transformed data it needs.
 */
fun BibleJson.initiateDatabase(database: Database): BibiliaDatabase {
  return when (database) {
    is MapDatabase -> BibiliaMapDatabase(this.verses().toMap())
  }
}

/**
 * Initialises all the databases.
 */
suspend fun initialiseDatabases(
  versions: List<VersionInfo>,
  databaseType: Database
) = versions.associateBy({ it.version }, { bible(it.url).initiateDatabase(databaseType) })

/**
 * Gives a list of supported versions
 */
fun versions(): List<VersionInfo> = listOf(
  VersionInfo(Version("Vulgate"), VULGATE_URL)
)

typealias BibiliaDatabases = Map<Version, BibiliaDatabase>