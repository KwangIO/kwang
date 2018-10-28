package kwang.backend.lwan

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kwang.RequestContext
import kwang.ResponseContext
import lwanc.*

internal class RequestContextLwan(private val cRequest: CPointer<lwan_request>): RequestContext {
    override fun getQuery(key: String) = lwan_request_get_query_param(cRequest, key)?.toKString()
    override fun getCookie(key: String) = lwan_request_get_cookie(cRequest, key)?.toKString()
    override fun getBodyParam(key: String) = lwan_request_get_post_param(cRequest, key)?.toKString()
    override val method
        get() = lwan_request_get_method(cRequest)
}

internal class ResponseContextLwan(private val cResponse: lwan_response) : ResponseContext {
    override fun end(body: String) = lwan_strbuf_set(cResponse.buffer, body, body.length.convert())
}