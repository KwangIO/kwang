package kwang

import kwang.type.StatusCode

interface Interceptor {
    fun handle(requestContext: RequestContext, responseContext: ResponseContext): StatusCode?
}