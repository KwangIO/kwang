package kwang.backend.lwan

import kotlinx.cinterop.*
import kwang.KwangHandler
import lwanc.*

fun staticProcess(
    req: CPointer<lwan_request>?,
    res: CPointer<lwan_response>?,
    data: COpaquePointer?
): lwan_http_status {
    initRuntimeIfNeeded()
    val handler = data?.asStableRef<KwangHandler>()?.get() ?: throw Exception("Cannot convert pointer into handler")
    return handler.process(RequestContextLwan(req!!), ResponseContextLwan(res!!.pointed, req))
}

class ServerLwan(private val handlers: List<KwangHandler> = emptyList(), private val config: LwanConfig? = null) {
    init {
        memScoped {
            val l = alloc<lwan>()
            if (config == null) {
                lwan_init(l.ptr)
            } else {
                val lwanConfig = alloc<lwan_config>()
                lwanConfig.apply {
                    allow_post_temp_file = config.allowPostTempFile
                    allow_cors = false
                    proxy_protocol = false
                    allow_post_temp_file = false
                    reuse_port = config.reusePort
                    expires = (1 * ONE_MINUTE).convert()
                    quiet = config.quite
                    n_threads = config.nThread.convert()
                    listener = config.listener.cstr.ptr
                    max_post_data_size = config.maxPostDataSize.convert()

                }
                lwan_init_with_config(l.ptr, lwanConfig.ptr)
            }
            val urlMap = allocArray<lwan_url_map>(handlers.size + 1)
            for (i in 0 until handlers.size) {
                urlMap[i].prefix = handlers[i].url.cstr.ptr
                urlMap[i].data = handlers[i].kwangHandler
                urlMap[i].handler = staticCFunction(::staticProcess)
            }
            urlMap[handlers.size].prefix = null
            lwan_set_url_map(l.ptr, urlMap)
            lwan_main_loop(l.ptr)
            lwan_shutdown(l.ptr)
        }
    }
}