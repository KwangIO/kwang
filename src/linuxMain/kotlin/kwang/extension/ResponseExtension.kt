package kwang.extension

import kwang.ResponseContext
import kwang.type.Header

fun ResponseContext.setHeaders(vararg headers: Header): ResponseContext = setHeaders(*headers)

infix fun ResponseContext.withHeaders(headers: List<Header>) = setHeaders(headers)

infix fun ResponseContext.json(body: String) = respond(body, "application/json")
infix fun ResponseContext.plain(body: String) = respond(body)