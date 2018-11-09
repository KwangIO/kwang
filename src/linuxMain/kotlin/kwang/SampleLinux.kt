package kwang

import kwang.backend.lwan.LwanConfig
import kwang.backend.lwan.ServerLwan
import kwang.extension.json
import kwang.extension.plain
import kwang.extension.withHeaders
import kwang.type.Header
import kwang.type.StatusCode

class SampleHandler : KwangHandler("/") {
    override fun handleGet(request: RequestContext, response: ResponseContext): StatusCode {
        println("Auth: ${request.authorization}")
        println("Origin:" + request.getHeader("Origin"))
        response withHeaders listOf(
            Header("meaning-of-life", "42"),
            Header("looking-for", "job")
        ) plain ("123")
        return StatusCode(200u)
    }
}

class OtherSample : KwangHandler("/hello") {
    override fun handleGet(request: RequestContext, response: ResponseContext): StatusCode {
        response json ("""{"hello":"${request.getQuery("name")}"}""")
        return StatusCode(200u)
    }
}

fun main(args: Array<String>) {
    ServerLwan(listOf(SampleHandler(), OtherSample()), LwanConfig("localhost:8081"))
}
