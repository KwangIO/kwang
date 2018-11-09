package kwang

import kotlinx.cinterop.StableRef
import kwang.type.HttpMethod
import kwang.type.StatusCode
import lwanc.HTTP_NOT_IMPLEMENTED

abstract class KwangHandler(
    val url: String
) {
    val kwangHandler by lazy { StableRef.create(this).asCPointer() }

    lateinit var interceptors: Collection<Interceptor>

    internal fun process(request: RequestContext, response: ResponseContext): StatusCode {
        interceptors.forEach {
            val res = it.handle(request, response)
            if (res != null) return res
        }
        return when (request.method) {
            HttpMethod.GET -> handleGet(request, response)
            HttpMethod.POST -> handlePost(request, response)
            HttpMethod.DELETE -> handleDelete(request, response)
            HttpMethod.PUT -> handlePut(request, response)
            else -> StatusCode(HTTP_NOT_IMPLEMENTED)
        }
    }

    open fun handleDelete(request: RequestContext, response: ResponseContext): StatusCode =
        StatusCode(HTTP_NOT_IMPLEMENTED)

    open fun handleGet(request: RequestContext, response: ResponseContext): StatusCode =
        StatusCode(HTTP_NOT_IMPLEMENTED)

    open fun handlePost(request: RequestContext, response: ResponseContext): StatusCode =
        StatusCode(HTTP_NOT_IMPLEMENTED)

    open fun handlePut(request: RequestContext, response: ResponseContext): StatusCode =
        StatusCode(HTTP_NOT_IMPLEMENTED)
}