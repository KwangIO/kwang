package kwang.backend.lwan

import kotlinx.cinterop.*
import kwang.KwangHandler
import lwanc.*

@ExperimentalUnsignedTypes
fun staticProcess(
    req: CPointer<lwan_request>?,
    res: CPointer<lwan_response>?,
    data: COpaquePointer?
): lwan_http_status {
    initRuntimeIfNeeded()
    asJson(res)
    val handler = data?.asStableRef<KwangHandler>()?.get() ?: throw Exception("Cannot convert pointer into handler")
    return handler.process(RequestContextLwan(req!!), ResponseContextLwan(res!!.pointed))
}


@ExperimentalUnsignedTypes
class KwangServer(private val handlers: List<KwangHandler> = emptyList()) {
    init {
        memScoped {
            val l = alloc<lwan>()
            lwan_init(l.ptr)
            val urlMap = memScope.allocArray<lwan_url_map>(handlers.size + 1)
            for (i in 0 until handlers.size) {
                urlMap[i].prefix = handlers[i].url.cstr.ptr
                urlMap[i].handler = staticCFunction(::staticProcess)
                urlMap[i].data = handlers[i].kwangHandler
            }
            urlMap[handlers.size].prefix = null
            lwan_set_url_map(l.ptr, urlMap)
            lwan_main_loop(l.ptr)
            lwan_shutdown(l.ptr)
        }
    }
}