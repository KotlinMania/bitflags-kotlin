# port-lint Proposed Changes

**Generated:** 2026-08-24
**Source:** tmp/bitflags
**Target:** src

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `commonMain/kotlin/io/github/kotlinmania/bitflags/Iter.kt` | `// port-lint: source iter.rs` | `// port-lint: source iter.rs` | `iter.rs` | `port-lint provenance header matched only after fallback normalization: 'iter.rs' vs expected 'iter.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/bitflags/Traits.kt` | `// port-lint: source traits.rs` | `// port-lint: source traits.rs` | `traits.rs` | `port-lint provenance header matched only after fallback normalization: 'traits.rs' vs expected 'traits.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/bitflags/Parser.kt` | `// port-lint: source parser.rs` | `// port-lint: source parser.rs` | `parser.rs` | `port-lint provenance header matched only after fallback normalization: 'parser.rs' vs expected 'parser.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/bitflags/ExampleGenerated.kt` | `// port-lint: source example_generated.rs` | `// port-lint: source example_generated.rs` | `example_generated.rs` | `port-lint provenance header matched only after fallback normalization: 'example_generated.rs' vs expected 'example_generated.rs'` |
