# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 4/98 (4.1%)
- **Function parity:** 45/248 matched (target 76) — 18.1%
- **Class/type parity:** 16/46 matched (target 22) — 34.8%
- **Combined symbol parity:** 61/294 matched (target 98) — 20.7%
- **Average inline-code cosine:** 0.49 (function body across 4 matched files)
- **Average documentation cosine:** 0.73 (doc text across 4 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 2 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **tests.flags** (25 deps)
   - Path: `src/tests/flags.rs`
   - Essential for 25 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. iter

- **Target:** `bitflags.Iter [PROVENANCE-FALLBACK]`
- **Similarity:** 0.56
- **Dependents:** 0
- **Priority Score:** 20804.4
- **Functions:** 4/4 matched (target 12)
- **Missing functions:** _none_
- **Types:** 2/4 matched (target 6)
- **Missing types:** `Iter`, `IterNames`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `iter.rs` vs expected `iter.rs`
- **Proposed provenance header:** `// port-lint: source iter.rs` (current: `// port-lint: source iter.rs`)
- **Lint issues:** 1

### 2. traits

- **Target:** `bitflags.Traits [PROVENANCE-FALLBACK]`
- **Similarity:** 0.68
- **Dependents:** 0
- **Priority Score:** 4003.2
- **Functions:** 31/31 matched (target 32)
- **Missing functions:** _none_
- **Types:** 9/9 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `traits.rs` vs expected `traits.rs`
- **Proposed provenance header:** `// port-lint: source traits.rs` (current: `// port-lint: source traits.rs`)
- **Lint issues:** 1

### 3. parser

- **Target:** `bitflags.Parser [PROVENANCE-FALLBACK]`
- **Similarity:** 0.72
- **Dependents:** 0
- **Priority Score:** 1502.8
- **Functions:** 10/10 matched (target 18)
- **Missing functions:** _none_
- **Types:** 5/5 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `parser.rs` vs expected `parser.rs`
- **Proposed provenance header:** `// port-lint: source parser.rs` (current: `// port-lint: source parser.rs`)
- **Lint issues:** 1

### 4. example_generated

- **Target:** `bitflags.ExampleGenerated [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 14)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `example_generated.rs` vs expected `example_generated.rs`
- **Proposed provenance header:** `// port-lint: source example_generated.rs` (current: `// port-lint: source example_generated.rs`)
- **Lint issues:** 1

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `src/lib.rs` | `Lib.kt` |

