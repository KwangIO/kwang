package kwang.backend.lwan

import kotlinx.cinterop.*
import kwang.ResponseContext
import kwang.type.Header
import lwanc.*

internal class ResponseContextLwan(
    private val cResponse: lwan_response, private val cRequest: CPointer<lwan_request>
) : ResponseContext {
    private val headers = mutableListOf<Header>()
    override fun addHeaders(headers: Collection<Header>): ResponseContext {
        this.headers.addAll(headers)
        return this
    }
    override fun sendChunk(body: String) { // TODO: Implement me
        lwan_strbuf_set(cResponse.buffer, body, body.length.convert())
        lwan_response_send_chunk(cRequest)
    }

    override fun respond(body: String, mimeType: String) {
        cResponse.headers = headers.toKeyValues()
        lwan_set_mime_type(cResponse.ptr, mimeType)
        lwan_strbuf_set(cResponse.buffer, body, body.length.convert())
    }
}