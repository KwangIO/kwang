package kwang.type

inline class StatusCode(val code: UInt) {
    operator fun compareTo(statusCode: StatusCode): Int = code.compareTo(statusCode.code)
}