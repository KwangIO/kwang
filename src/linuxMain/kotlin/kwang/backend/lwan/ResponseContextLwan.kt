package kwang.backend.lwan

import kotlinx.cinterop.convert
import kotlinx.cinterop.cstr
import kotlinx.cinterop.ptr
import kwang.ResponseContext
import lwanc.lwan_response
import lwanc.lwan_set_mime_type
import lwanc.lwan_strbuf_set

internal class ResponseContextLwan(private val cResponse: lwan_response) : ResponseContext {
    override fun setHeader() = TODO()
    override fun end(body: String, mimeType: String) {
        lwan_set_mime_type(cResponse.ptr, mimeType.cstr)
        lwan_strbuf_set(cResponse.buffer, body, body.length.convert())
    }
}