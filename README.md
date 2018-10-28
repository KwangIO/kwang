# Kwang
Kwang is a thin Kotlin/Native wrapper around high-performance, low-overhead web server(s).
It is in experimental state, supporting [Lwan](https://github.com/lpereira/lwan/) partially. It may have [libh2o](https://h2o.examp1e.net/) backend in the feature.

## Building
### Cloning the repo with submodule(s)
```
git clone --recurse-submodules https://github.com/KwangIO/kwang
cd kwang
```
You can import it with IntelliJ IDEA
### Building Lwan
```
./buildLwan.sh
```
If you want more customization, see [lwan#Building](https://github.com/lpereira/lwan#building) for how manually build it

### Sample
Open `SampleLinux.kt`
```kotlin
class SampleHandler: KwangHandlerLwan("/") {
    override fun handleGet(context: Context): UInt {
        return if (context.response.end("123")) 200u else 500u
    }
}

fun main(args: Array<String>) {
    KwangServer(listOf(SampleHandler()))
}
```

#### Running the sample
`gradle runProgram` (no colorful output) or  `gradle build` (test will fail at the moment, just ignore it and run `build/bin/linux/main/release/executable/kwang.kexe`)

The server will start on `localhost:8080`

### Building klib
The library is very EXPERIMENTAL and likely to change significantly, using it in production code is NOT RECOMMENDED. But yes, you can build the `klib` (check `gradle linuxKlibrary`)

### Suported operating system
Linux64

## Roadmap
* [ ] Logging
* [ ] H2O backend
* [ ] Authentication
* [ ] Finsish the wrapper
* [ ] Optimize performance
* [ ] Ready to use `klib`
* [ ] Template support (low priority)
* [ ] Eventbus
* [ ] WebSocket
