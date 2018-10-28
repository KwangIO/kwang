package kwang

import kwang.backend.lwan.KwangHandlerLwan
import kwang.backend.lwan.KwangServer


@ExperimentalUnsignedTypes
class SampleHandler: KwangHandlerLwan("/") {
    override fun handleGet(context: Context): UInt {
        return if (context.response.end("123")) 200u else 500u
    }
}

@ExperimentalUnsignedTypes
fun main(args: Array<String>) {
    KwangServer(listOf(SampleHandler()))
}
