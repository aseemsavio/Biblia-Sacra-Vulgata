package com.aseemsavio.bibilia.database.extensions

import com.aseemsavio.bibilia.data.*
import com.aseemsavio.bibilia.database.api.BibiliaDatabase
import com.aseemsavio.bibilia.database.api.BibiliaMapDatabase
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.net.URL

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
 * Initialises all the versions' databases.
 */
suspend fun initialiseDatabases(
    versions: List<VersionInfo>,
    databaseType: Database
) = versions.pMap { it.version to bible(it.url).initiateDatabase(databaseType) }.toMap()

/**
 * Gives a list of supported versions
 */
fun versions(configData: ConfigData): List<VersionInfo> = configData.versionInfo.map {
    VersionInfo(Version(it.name), URL(it.link))
}

typealias BibiliaDatabases = Map<Version, BibiliaDatabase>

/**
 * Getting a [Database] type from a [String].
 * New databases when added need to be added here.
 */
val String.db get() = when(this) {
    "Map" -> MapDatabase
    else -> throw IllegalArgumentException("Unknown database type requested!")
}
