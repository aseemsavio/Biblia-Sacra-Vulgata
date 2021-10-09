package com.aseemsavio.biblia.data

suspend fun theBible() = verses()

private suspend fun verses(): List<Verse> = bible().chapters().verses()
