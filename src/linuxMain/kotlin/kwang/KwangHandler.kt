package kwang

import kotlinx.cinterop.StableRef
import kwang.type.StatusCode
import lwanc.HTTP_NOT_IMPLEMENTED
import lwanc.REQUEST_METHOD_DELETE
import lwanc.REQUEST_METHOD_GET
import lwanc.REQUEST_METHOD_POST

abstract class KwangHandler(
    val url: String,
    private val interceptors: List<(req: RequestContext, res: ResponseContext) -> StatusCode> = emptyList()
) {
    val kwangHandler by lazy { StableRef.create(this).asCPointer() }

    internal fun process(request: RequestContext, response: ResponseContext): StatusCode {
        interceptors.forEach { f->
            val res = f(request, response)
            if (res >= StatusCode(300u)) return res
        }
        return when (request.method) {
            REQUEST_METHOD_GET -> handleGet(request, response)
            REQUEST_METHOD_POST -> handlePost(request, response)
            REQUEST_METHOD_DELETE -> handleDelete(request, response)
            else -> StatusCode(HTTP_NOT_IMPLEMENTED)
        }
    }

    open fun handleDelete(request: RequestContext, response: ResponseContext): StatusCode = StatusCode(HTTP_NOT_IMPLEMENTED)
    open fun handleGet(request: RequestContext, response: ResponseContext): StatusCode = StatusCode(HTTP_NOT_IMPLEMENTED)
    open fun handlePost(request: RequestContext, response: ResponseContext): StatusCode = StatusCode(HTTP_NOT_IMPLEMENTED)
}