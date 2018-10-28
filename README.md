# Kwang
Kwang is a thin Kotlin/Native wrapper around high-performance, low-overhead web server(s).
It is in experimental state, supporting [Lwan](https://github.com/lpereira/lwan/) partially. It may has [libh2o](https://h2o.examp1e.net/) backend in the feature.

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
* [ ] Server configuration (`port`, ..)
* [ ] Logging
* [ ] H2O backend
* [ ] Authentication
* [ ] Finsish the wrapper
* [ ] Optimize performance
* [ ] Template support (low priority)
* [ ] EventBus
* [ ] WebSocket

## Benchmark
TODO
In case you want benchmarking Kwang, you should consider compile lwan in release mode and pass `-opt` to `konanc`
With `autocannon -c 100 -d 40 -p 10 localhost:8080`, `Kwang` reached around 80k reqs/sec on a MSI GE72VR laptop

## Contributing
Feel free to submit issues and enhancement requests. This is a low priority side-project to me, so there is no ETA/promise, but feel free to open PRs.

## Why? JVM framework like Vert.X/Ktor/Spring + GraalVM makes more sense
Agree. 

## Known issue
For current Kotlin/Native version (`1.3.0-rc-190`), it will not compile due to linker issue (please clone Kotlin/Native from github or wait for the next release)

(Kudos to Kotlin/Native team)
