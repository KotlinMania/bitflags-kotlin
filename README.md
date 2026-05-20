# bitflags-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Fbitflags--kotlin-blue.svg)](https://github.com/KotlinMania/bitflags-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/bitflags-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/bitflags-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/bitflags-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/bitflags-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`bitflags`](https://github.com/bitflags/bitflags).

**Original Project:** This port is based on [`bitflags`](https://github.com/bitflags/bitflags). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:bitflags-kotlin:0.1.1")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / x64 / simulator-arm64 (Swift export + XCFramework)
- tvOS arm64 / simulator-arm64
- watchOS arm32 / arm64 / device-arm64 / simulator-arm64
- Android (API 24+)
- Android Native arm32 / arm64 / x64 / x86
- JVM
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Wasm-WASI

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same MIT OR Apache-2.0 license terms as the upstream [`bitflags`](https://github.com/bitflags/bitflags). See [LICENSE-MIT](LICENSE-MIT) and [LICENSE-APACHE](LICENSE-APACHE) for the full text.

Original work copyrighted by the bitflags authors.
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`bitflags`](https://github.com/bitflags/bitflags) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
