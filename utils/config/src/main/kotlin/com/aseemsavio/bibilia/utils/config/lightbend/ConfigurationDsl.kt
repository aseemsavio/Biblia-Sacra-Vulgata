package com.aseemsavio.bibilia.utils.config.lightbend

import com.typesafe.config.Config

/**
 * Gives the [Int] value of a config property
 */
fun Config.int(fn: () -> String): Int = getInt(fn())

/**
 * Gives the [String] value of a config property
 */
fun Config.string(fn: () -> String): String = getString(fn())

/**
 * Gives the [List] of [String] values of a config property
 */
fun Config.strings(fn: () -> String): List<String> = getStringList(fn())

/**
 * Gives the [Boolean] value of a config property
 */
fun Config.boolean(fn: () -> String): Boolean = getBoolean(fn())

/**
 * Gives a [List] of [Config] objects of a config property
 */
fun Config.list(fn: () -> String): List<Config> = getConfigList(fn())
