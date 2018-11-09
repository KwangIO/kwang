package kwang

import kwang.type.StatusCode

interface Interceptor {
    fun handle(request: RequestContext, response: ResponseContext): StatusCode?
}