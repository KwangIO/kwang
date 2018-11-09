package kwang

import kwang.type.Header

interface RequestContext {
    fun getQuery(key: String): String?
    fun getCookie(key: String): String?
    fun getBodyParam(key: String): String?
    val requestBody: String?
    val method: Int
    val authorization: String?
    val contentType: String?
    fun getHeader(key: String): String?
}

interface ResponseContext {
    fun respond(body: String, mimeType: String = "text/plain")
    fun sendChunk(body: String)
    fun addHeaders(headers: Collection<Header>) : ResponseContext
}