# BitFlags publication consumer

This separate build declares only the staged producer root coordinate and Kotlin test.
The root, Android AAR, JVM JAR and macOS ARM64 KLIB coordinates resolve exclusively
from the absolute producerRepository property. Dependencies resolve from Central/Google;
no included project, source substitution or mavenLocal is used.

Stage KotlinMultiplatform, Android, JVM and MacosArm64 publications to
CentralPortalStagingRepository before running compileAndroidMain,
compileAndroidHostTest, testAndroidHostTest, jvmTest and macosArm64Test with the
same producerVersion. Set both Android SDK environment variables to the same SDK.

Four named common tests must execute on each of Android host, JVM and macOS ARM64.
Both consumer-owned public subclasses are intentionally visible to Native compilation:
the previous hidden producer superclass must be rejected rather than hidden in the caller.
Android raw compile/runtime classpaths are checked independently for duplicate classes.
This is not Android-device execution, Linux/Windows runtime coverage, or Swift validation.


## JS and Wasm scope

The separate build also resolves JS, WasmJS and WASI root-coordinate variants exclusively
from staging. Six named common tests execute independently on each Node runner. Selected
component, platform attributes and payload hashes are retained per target. These are
Kotlin-authored external KLIB consumers, not plain JavaScript callers of a product export.

The producer has no JsExport declarations, TypeScript-declaration generation or main
JavaScript/Wasm binary configuration. Its existing generated JavaScript/Wasm executables
are test programs. No direct product JS/TypeScript surface, runtime wrong-type boundary
or original ParseError-object identity is certified: fromStr currently throws an
IllegalArgumentException carrying diagnostic text rather than returning ParseError.
The immutable insert/remove return convention is tested explicitly; it is not Rust
Copy-variable mutation parity. WASI KLIB/runtime evidence is separate from WasmJS interop;
no JavaScript or TypeScript product API is invented for WASI.
