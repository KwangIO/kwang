package kwang.extension

import kwang.ResponseContext
import kwang.type.Header

fun ResponseContext.addHeaders(vararg headers: Header): ResponseContext = addHeaders(headers.toList())

infix fun ResponseContext.withHeaders(headers: List<Header>) = addHeaders(headers)

infix fun ResponseContext.json(body: String) = respond(body, "application/json")
infix fun ResponseContext.plain(body: String) = respond(body)