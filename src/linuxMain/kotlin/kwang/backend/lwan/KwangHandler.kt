package kwang.backend.lwan

import kotlinx.cinterop.*
import kwang.Context
import kwang.KwangHandler
import lwanc.*

@ExperimentalUnsignedTypes
abstract class KwangHandlerLwan(val url: String) : KwangHandler {
    val kwangHandler by lazy { StableRef.create(this) }

    val cHandler = staticCFunction(::staticProcess)

    override fun process(context: Context): UInt {
        return when (context.request.method) {
            REQUEST_METHOD_GET -> handleGet(context)
            REQUEST_METHOD_POST -> handlePost(context)
            REQUEST_METHOD_DELETE -> handleDelete(context)
            else -> HTTP_NOT_IMPLEMENTED
        }
    }

    override fun handleDelete(context: Context): UInt = HTTP_NOT_IMPLEMENTED
    override fun handleGet(context: Context): UInt = HTTP_NOT_IMPLEMENTED
    override fun handlePost(context: Context): UInt = HTTP_NOT_IMPLEMENTED
}

@ExperimentalUnsignedTypes
fun staticProcess(
    req: CPointer<lwan_request>?,
    res: CPointer<lwan_response>?,
    data: COpaquePointer?
): lwan_http_status {
    initRuntimeIfNeeded()
    asJson(res)
    val handler = data?.asStableRef<KwangHandlerLwan>()?.get() ?: throw Exception("Cannot convert pointer into handler")
    return handler.process(Context(RequestContextLwan(req!!), ResponseContextLwan(res!!.pointed)))
}