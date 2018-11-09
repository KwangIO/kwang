package kwang.interceptor

import kwang.Interceptor
import kwang.RequestContext
import kwang.ResponseContext
import kwang.extension.addHeaders
import kwang.extension.plain
import kwang.type.Header
import kwang.type.HttpMethod
import kwang.type.ORIGIN
import kwang.type.StatusCode

private const val ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials"
private const val ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin"

class CorsInterceptor(allowedOriginPattern: String, private val allowCredentials: Boolean = false): Interceptor {
    private val allowedOrigin = Regex(allowedOriginPattern, RegexOption.IGNORE_CASE)
    private fun isValidOrigin(origin: String) = allowedOrigin.matches(origin)


    override fun handle(request: RequestContext, response: ResponseContext): StatusCode? {
        val origin = request.getHeader(ORIGIN) ?: return null
        if (isValidOrigin(origin)) {
            response.addHeaders(Header(ACCESS_CONTROL_ALLOW_ORIGIN, origin))
            if (allowCredentials) response.addHeaders(Header(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true"))
            if (request.method == HttpMethod.OPTIONS) {
                return StatusCode(200u)
            }
        } else {
            response plain "CORS rejected"
            return StatusCode(403u)
        }
        return null
    }
}