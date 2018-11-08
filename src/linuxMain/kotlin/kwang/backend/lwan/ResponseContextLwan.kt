package kwang.backend.lwan

import kotlinx.cinterop.*
import kwang.ResponseContext
import kwang.type.Header
import lwanc.*

internal class ResponseContextLwan(
    private val cResponse: lwan_response, private val cRequest: CPointer<lwan_request>
) : ResponseContext {
    override fun setHeaders(headers: List<Header>): ResponseContext {
        cResponse.headers = headers.toKeyValues()
        return this
    }
    override fun sendChunk(body: String) { // TODO: Implement me
        lwan_strbuf_set(cResponse.buffer, body, body.length.convert())
        lwan_response_send_chunk(cRequest)
    }

    override fun respond(body: String, mimeType: String) {
        lwan_set_mime_type(cResponse.ptr, mimeType)
        lwan_strbuf_set(cResponse.buffer, body, body.length.convert())
    }
}