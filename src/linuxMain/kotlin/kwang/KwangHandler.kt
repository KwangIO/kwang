package kwang

internal interface KwangHandler {
    fun process(context: Context): UInt
    fun handleDelete(context: Context): UInt
    fun handleGet(context: Context): UInt
    fun handlePost(context: Context): UInt
}