package kwang

interface RequestContext {
    fun getQuery(key: String): String?
    fun getCookie(key: String): String?
    fun getBodyParam(key: String): String?
    val method: Int
    val authorization: String?
}

interface ResponseContext {
    fun setHeader()
    fun end(body: String, mimeType: String = "text/plain")
}