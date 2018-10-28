package kwang

interface RequestContext {
    fun getQuery(key: String): String?
    fun getCookie(key: String): String?
    fun getBodyParam(key: String): String?
    val method: Int
}

interface ResponseContext {
    fun end(body: String): Boolean
}

data class Context(val request: RequestContext, val response: ResponseContext)