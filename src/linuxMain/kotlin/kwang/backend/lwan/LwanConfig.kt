package kwang.backend.lwan

import lwanc.DEFAULT_BUFFER_SIZE

data class LwanConfig(
    val listener: String = "localhost:8080",
    val nThread: Int = 0,
    val maxPostDataSize: Int = 10 * DEFAULT_BUFFER_SIZE,
    val quite: Boolean = false,
    val allowPostTempFile: Boolean = false,
    val reusePort: Boolean = false,
    val proxyProtocol: Boolean = false
)