# port-lint Proposed Changes

**Generated:** 2026-08-31
**Source:** tmp/bitflags
**Target:** src/commonMain/kotlin

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/bitflags/Iter.kt` | `// port-lint: source iter.rs` | `// port-lint: source iter.rs` | `iter.rs` | `port-lint provenance header matched only after fallback normalization: 'iter.rs' vs expected 'iter.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/bitflags/IterTest.kt` | `// port-lint: tests iter.rs` | `// port-lint: tests iter.rs` | `iter.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:iter.rs' vs expected 'iter.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/bitflags/Traits.kt` | `// port-lint: source traits.rs` | `// port-lint: source traits.rs` | `traits.rs` | `port-lint provenance header matched only after fallback normalization: 'traits.rs' vs expected 'traits.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/bitflags/Parser.kt` | `// port-lint: source parser.rs` | `// port-lint: source parser.rs` | `parser.rs` | `port-lint provenance header matched only after fallback normalization: 'parser.rs' vs expected 'parser.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/bitflags/ParserTest.kt` | `// port-lint: tests parser.rs` | `// port-lint: tests parser.rs` | `parser.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:parser.rs' vs expected 'parser.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/bitflags/TestTypes.kt` | `// port-lint: source tests.rs` | `// port-lint: source tests.rs` | `tests.rs` | `port-lint provenance header matched only after fallback normalization: 'tests.rs' vs expected 'tests.rs'` |
