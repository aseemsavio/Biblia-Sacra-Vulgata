package com.aseemsavio.biblia.routes.dsl

import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.IllegalArgumentException

/**
 * Returns the value of a path parameter as a [String].
 */
fun RoutingContext.pp(fn: () -> String): String = pathParam(fn())

/**
 * Returns the value of a path parameter as an [Int].
 */
fun RoutingContext.ppAsInt(fn: () -> String): Int = pathParam(fn()).toInt()

/**
 * Handles success responses. Takes in an optional status code (defaulted to 200) and a text or JSON payload.
 */
fun RoutingContext.ok(statusCode: Int = 200, fn: RestResponseBuilder.() -> Unit) {
  if (statusCode in 200..299) this.request().response().statusCode = statusCode
  else throw IllegalArgumentException("Success status codes must be between 200 and 299.")
  RestResponseBuilder().apply(fn).build(this)
}

/**
 * Builds the REST response.
 */
class RestResponseBuilder {
  var json: String? = null
  private var text: String? = null

  val format: Json = Json { encodeDefaults = false }

  /**
   * Serialises an object annotated with @[Serializable] into a JSON string.
   */
  inline fun <reified T> json(fn: () -> T?) {
    this.json = format.encodeToString(fn())
  }

  /**
   * Serialises a Kotlin/Java object to plain text.
   */
  fun text(fn: () -> Any?) {
    this.text = fn()?.toString()
  }

  fun build(context: RoutingContext) {
    if (json != null && text != null)
      throw IllegalArgumentException("Only one of the properties (json, text) is allowed to be set.")

    if (json == null && text == null)
      throw IllegalArgumentException("At least one of the properties (json, text) should be set.")

    context.request().response()
      .also {
        if (json == null) {
          it.putHeader("content-type", "text/plain")
          it.end(text)
        } else {
          it.putHeader("content-type", "application/json")
          it.end(json)
        }
      }
  }
}
