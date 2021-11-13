package com.aseemsavio.bibilia.utils.config

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject

/**
 * Gives the [String] value of a nested or outer level JSON environmental variable.
 */
fun JsonObject.value(default: String = "", fn: () -> String): String = getString(fn(), default)

/**
 * Gives the [Int] value of a nested or outer level JSON environmental variable.
 */
fun JsonObject.valueAsInt(default: Int = 0, fn: () -> String): Int = getString(fn(), default.toString()).toInt()

/**
 * Gives the [JsonObject] belonging to a JSON environmental variable.
 */
fun JsonObject.obj(fn: () -> String): JsonObject = getJsonObject(fn())

/**
 * Gives the [JsonArray] belonging to a JSON environmental variable.
 */
fun JsonObject.array(fn: () -> String): JsonArray = getJsonArray(fn())

/**
 * Gives a [List] of [String]s from a [JsonArray]
 */
fun JsonArray.stringList(fn: () -> String) = map { (it as JsonObject).value { fn() } }

/**
 * Gives the [String]s from a [JsonArray] of Strings.
 */
fun JsonArray.stringList() = map { it.toString() }
