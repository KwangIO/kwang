# Kwang
Kwang is a thin Kotlin/Native wrapper around high-performance, low-overhead web server(s).
It is in experimental state, supporting [Lwan](https://github.com/lpereira/lwan/) partially. It may has [libh2o](https://h2o.examp1e.net/) backend in the future.

[![Build Status](https://travis-ci.com/KwangIO/kwang.svg?branch=master)](https://travis-ci.com/KwangIO/kwang)
[![Build status](https://quangio.visualstudio.com/Kwang/_apis/build/status/Kwang-Gradle-CI)](https://quangio.visualstudio.com/Kwang/_build/latest?definitionId=1)
[![codebeat badge](https://codebeat.co/badges/63348e80-82c4-484f-9cd2-ff85dea61f36)](https://codebeat.co/projects/github-com-kwangio-kwang-master)
[![CodeFactor](https://www.codefactor.io/repository/github/kwangio/kwang/badge/master)](https://www.codefactor.io/repository/github/kwangio/kwang/overview/master)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/KwangIO/kwang.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/KwangIO/kwang/alerts/)
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
class SampleHandler: KwangHandler("/") {
    override fun handleGet(request: RequestContext, response: ResponseContext): UInt {
        println("Auth: ${request.authorization}")
        response
            .setHeaders(listOf(Pair("meaning-of-life", "42"), Pair("looking-for", "job")))
            .respond("123", "application/json")
        return 200u
    }
}

fun main(args: Array<String>) {
    ServerLwan(listOf(SampleHandler(), OtherSample()), LwanConfig("localhost:8081"))
}

```

#### Running the sample
`gradle runProgram` (no colorful output) or  `gradle build` (test will fail at the moment, just ignore it and run `build/bin/linux/main/release/executable/kwang.kexe`)

The server will start on `localhost:8081`

### Building klib
The library is very EXPERIMENTAL and likely to change significantly, using it in production code is NOT RECOMMENDED. But yes, you can build the `klib` (check `gradle linuxKlibrary`)

### Supported operating system
Linux64

## Roadmap
* [x] Server configuration (`port`, ..)
* [ ] Logging
* [ ] H2O backend (because Lwan does not support HTTP/2)
* [ ] Authentication
* [ ] Finish the wrapper
* [ ] Optimize performance
* [ ] Template support (low priority)
* [ ] EventBus
* [ ] WebSocket
* [ ] Path params


## Benchmark
TODO
In case you want benchmarking Kwang, you should consider compile lwan in release mode and pass `-opt` to `konanc`
With `autocannon -c 100 -d 40 -p 10 localhost:8080`, `Kwang` reached around 80k reqs/sec on a MSI GE72VR laptop

## Contributing
Feel free to submit issues and enhancement requests. This is a low priority side-project to me, so there is no ETA/promise, but PRs are welcomed. 

## Why? JVM framework like Vert.X/Ktor/Spring + GraalVM makes more sense
Agree. 

## Can you change the name, it looks weird
I am not in the mood of choosing bike shed's name. Moreover, it is not that weird you racist people! [Kwang in Korean](https://www.wikiwand.com/en/Kwang)

## Known issue
For current Kotlin/Native version (`1.3.0`), it will not compile due to linker issue (please clone Kotlin/Native from github or wait for the next release)

(Kudos to Kotlin/Native team)
