package com.aseemsavio.biblia.routes.dsl

import io.vertx.ext.web.RoutingContext

/**
 * Returns the value of a path parameter as a [String].
 */
fun RoutingContext.pp(fn: () -> String): String = pathParam(fn())

/**
 * Returns the value of a path parameter as an [Int].
 */
fun RoutingContext.ppAsInt(fn: () -> String): Int = pathParam(fn()).toInt()
