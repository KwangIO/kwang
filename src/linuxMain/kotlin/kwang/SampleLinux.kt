package kwang

import kwang.backend.lwan.LwanConfig
import kwang.backend.lwan.ServerLwan


@ExperimentalUnsignedTypes
class SampleHandler: KwangHandler("/") {
    override fun handleGet(request: RequestContext, response: ResponseContext): UInt {
        println("Auth: ${request.authorization}")
        return if (response.setMimeType("text/plain").end("123")) 200u else 500u
    }
}

class OtherSample: KwangHandler("/hello") {
    override fun handleGet(request: RequestContext, response: ResponseContext): UInt {
        response.setMimeType("text/plain").end("""{"hello":"${request.getQuery("name")}"}""")
        return 200u
    }
}


@ExperimentalUnsignedTypes
fun main(args: Array<String>) {
    ServerLwan(listOf(SampleHandler(), OtherSample()), LwanConfig("localhost:8081"))
}
