package com.aseemsavio.vertx.rest.dsl

import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.IllegalArgumentException

/**
 * Returns the value of a path parameter as a [String].
 */
fun RoutingContext.pp(fn: () -> String): String = pathParam(fn())

/**
 * Returns the query param as a String.
 * If null, it returns the default value.
 */
fun RoutingContext.qp(default: String = "", fn: () -> String): String = queryParam(fn())?.firstOrNull() ?: default

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
 * Handles not found requests.
 */
fun RoutingContext.notFound(fn: RestResponseBuilder.() -> Unit) {
  this.request().response().statusCode = 404
  RestResponseBuilder().apply(fn).build(this)
}

/**
 * Takes in a nullable response (This can be a nullable collection or a nullable object),
 * a success response (eg: [ok]) and a failure response (eg: [notFound]).
 * If the response is either null or empty, the failure function is executed.
 * For the flip case, the success function is executed.
 */
fun fold(response: Any?, success: () -> Unit, failure: () -> Unit) {
  when (response) {
    is Int -> if (response == 0) failure() else success() /* This is specific to this app only */
    is Collection<*> -> if (response.isEmpty()) failure() else success()
    else -> if (response == null) failure() else success()
  }
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

    if (json == null && text == null) {
      context.request().response().end()
      return
    }

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
