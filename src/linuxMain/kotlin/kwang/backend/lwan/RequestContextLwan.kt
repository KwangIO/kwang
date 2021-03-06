package kwang.backend.lwan

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString
import kwang.RequestContext
import kwang.type.HttpMethod
import lwanc.*

internal class RequestContextLwan(private val cRequest: CPointer<lwan_request>) : RequestContext {
    override val authorization = cRequest.pointed.helper?.let {
        it.pointed.authorization.value?.toKString()
    }

    override fun getQuery(key: String) = lwan_request_get_query_param(cRequest, key)?.toKString()
    override fun getCookie(key: String) = lwan_request_get_cookie(cRequest, key)?.toKString()
    override fun getBodyParam(key: String) = lwan_request_get_post_param(cRequest, key)?.toKString()
    override fun getHeader(key: String) = lwan_request_get_header(cRequest, key)?.toKString()

    override val requestBody
        get() = lwan_request_get_request_body(cRequest)?.toKString()
    override val contentType
        get() = lwan_request_get_content_type(cRequest)?.toKString()
    override val method
        get() = when(lwan_request_get_method(cRequest)) {
            REQUEST_METHOD_GET -> HttpMethod.GET
            REQUEST_METHOD_POST -> HttpMethod.POST
            REQUEST_METHOD_DELETE -> HttpMethod.DELETE
            REQUEST_METHOD_OPTIONS -> HttpMethod.OPTIONS
            else -> HttpMethod.INVALID
        }
}
