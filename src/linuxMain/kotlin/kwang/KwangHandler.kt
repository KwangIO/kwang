package kwang

import kotlinx.cinterop.StableRef
import lwanc.HTTP_NOT_IMPLEMENTED
import lwanc.REQUEST_METHOD_DELETE
import lwanc.REQUEST_METHOD_GET
import lwanc.REQUEST_METHOD_POST

abstract class KwangHandler(val url: String) {
    val kwangHandler by lazy { StableRef.create(this).asCPointer() }

    internal fun process(request: RequestContext, response: ResponseContext): UInt {
        return when (request.method) {
            REQUEST_METHOD_GET -> handleGet(request, response)
            REQUEST_METHOD_POST -> handlePost(request, response)
            REQUEST_METHOD_DELETE -> handleDelete(request, response)
            else -> HTTP_NOT_IMPLEMENTED
        }
    }
    open fun handleDelete(request: RequestContext, response: ResponseContext): UInt = HTTP_NOT_IMPLEMENTED
    open fun handleGet(request: RequestContext, response: ResponseContext): UInt = HTTP_NOT_IMPLEMENTED
    open fun handlePost(request: RequestContext, response: ResponseContext): UInt = HTTP_NOT_IMPLEMENTED
}