# port-lint Proposed Changes

**Generated:** 2026-08-26
**Source:** tmp/bitflags/src
**Target:** src/commonMain/kotlin/io/github/kotlinmania/bitflags

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonTest/kotlin/io/github/kotlinmania/bitflags/IterTest.kt` | `// port-lint: tests src/tests/iter.rs` | `// port-lint: tests iter.rs` | `iter.rs` | `port-lint provenance header matched only by basename: 'tests:src/tests/iter.rs' vs expected 'iter.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/bitflags/ParserTest.kt` | `// port-lint: tests src/tests/parser.rs` | `// port-lint: tests parser.rs` | `parser.rs` | `port-lint provenance header matched only by basename: 'tests:src/tests/parser.rs' vs expected 'parser.rs'` |
