package kwang.backend.lwan

import kotlinx.cinterop.*
import lwanc.*

@ExperimentalUnsignedTypes
class KwangServer(private val handlers: List<KwangHandlerLwan> = emptyList()) {
    init {
        memScoped {
            val l = alloc<lwan>()
            lwan_init(l.ptr)
            val urlMap = memScope.allocArray<lwan_url_map>(handlers.size + 1)
            for (i in 0 until handlers.size) {
                urlMap[i].prefix = handlers[i].url.cstr.ptr
                urlMap[i].handler = handlers[i].cHandler
                urlMap[i].data = handlers[i].kwangHandler.asCPointer()
            }
            urlMap[handlers.size].prefix = null
            lwan_set_url_map(l.ptr, urlMap)
            lwan_main_loop(l.ptr)
            lwan_shutdown(l.ptr)
        }
    }
}