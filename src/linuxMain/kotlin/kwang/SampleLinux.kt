package kwang

import kwang.backend.lwan.LwanConfig
import kwang.backend.lwan.ServerLwan
import kwang.extension.json
import kwang.extension.plain
import kwang.extension.withHeaders
import kwang.type.Header

@ExperimentalUnsignedTypes
class SampleHandler : KwangHandler("/") {
    override fun handleGet(request: RequestContext, response: ResponseContext): UInt {
        println("Auth: ${request.authorization}")
        response withHeaders listOf(
            Header("meaning-of-life", "42"),
            Header("looking-for", "job")
        ) plain ("123")
        return 200u
    }
}

class OtherSample : KwangHandler("/hello") {
    override fun handleGet(request: RequestContext, response: ResponseContext): UInt {
        response json ("""{"hello":"${request.getQuery("name")}"}""")
        return 200u
    }
}


@ExperimentalUnsignedTypes
fun main(args: Array<String>) {
    ServerLwan(listOf(SampleHandler(), OtherSample()), LwanConfig("localhost:8081"))
}
