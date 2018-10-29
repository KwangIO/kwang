package kwang

import kwang.backend.lwan.KwangServer


@ExperimentalUnsignedTypes
class SampleHandler: KwangHandler("/") {
    override fun handleGet(request: RequestContext, response: ResponseContext): UInt {
        return if (response.end("123")) 200u else 500u
    }
}

class OtherSample: KwangHandler("/hello") {
    override fun handleGet(request: RequestContext, response: ResponseContext): UInt {
        response.end("""{"hello":"${request.getQuery("name")}"}""")
        return 200u
    }
}


@ExperimentalUnsignedTypes
fun main(args: Array<String>) {
    println("1")
    KwangServer(listOf(SampleHandler(), OtherSample()))
}
