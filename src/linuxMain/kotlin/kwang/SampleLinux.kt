package kwang

import kwang.backend.lwan.LwanConfig
import kwang.backend.lwan.ServerLwan


@ExperimentalUnsignedTypes
class SampleHandler: KwangHandler("/") {
    override fun handleGet(request: RequestContext, response: ResponseContext): UInt {
        println("Auth: ${request.authorization}")
        response.setHeaders(listOf(Pair("haha", "abc"))).respond("123")
        return 200u
    }
}

class OtherSample: KwangHandler("/hello") {
    override fun handleGet(request: RequestContext, response: ResponseContext): UInt {
        response.respond("""{"hello":"${request.getQuery("name")}"}""")
        return 200u
    }
}


@ExperimentalUnsignedTypes
fun main(args: Array<String>) {
    ServerLwan(listOf(SampleHandler(), OtherSample()), LwanConfig("localhost:8081"))
}
