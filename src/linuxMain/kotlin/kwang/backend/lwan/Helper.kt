package kwang.backend.lwan

import kotlinx.cinterop.*
import lwanc.lwan_key_value
import lwanc.lwan_set_key_value
import lwanc.lwan_value

internal fun CPointer<lwan_value>?.toKString(): String? =
    this?.let { it.pointed.value?.toKString() }

internal fun List<Pair<String, String>>.toKeyValues(): CPointer<lwan_key_value> {
    val m = this
    val res = nativeHeap.allocArray<lwan_key_value>(m.size + 1) // TODO: Check for memory leak
    m.forEachIndexed { i, (k, v) ->
        lwan_set_key_value(res[i].ptr, k, v)
    }
    return res
}