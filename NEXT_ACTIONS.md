# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 4/42 (9.5%)
- **Function parity:** 32/134 matched (target 58) — 23.9%
- **Class/type parity:** 4/20 matched (target 10) — 20.0%
- **Combined symbol parity:** 36/154 matched (target 68) — 23.4%
- **Average inline-code cosine:** 0.20 (function body across 4 matched files)
- **Average documentation cosine:** 0.68 (doc text across 4 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 3 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **tests.flags** (25 deps)
   - Path: `tests/flags.rs`
   - Essential for 25 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. parser

- **Target:** `bitflags.Parser`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 131510.0
- **Functions:** 0/10 matched (target 6)
- **Missing functions:** `to_writer`, `fmt`, `from_str`, `to_writer_truncate`, `from_str_truncate`, `to_writer_strict`, `from_str_strict`, `invalid_hex_flag`, `invalid_named_flag`, `empty_flag`
- **Types:** 2/5 matched (target 2)
- **Missing types:** `AsDisplay`, `WriteHex`, `ParseHex`

### 2. traits

- **Target:** `bitflags.Traits`
- **Similarity:** 0.65
- **Dependents:** 0
- **Priority Score:** 84003.5
- **Functions:** 30/31 matched
- **Missing functions:** `new`
- **Types:** 2/9 matched (target 2)
- **Missing types:** `Flags`, `Bits`, `Primitive`, `PublicFlags`, `Iter`, `IterNames`, `ImplementedByBitFlagsMacro`

### 3. iter

- **Target:** `bitflags.Iter`
- **Similarity:** 0.17
- **Dependents:** 0
- **Priority Score:** 60808.3
- **Functions:** 2/4 matched (target 7)
- **Missing functions:** `new`, `__private_const_new`
- **Types:** 0/4 matched
- **Missing types:** `Iter`, `Item`, `IterNames`, `IterDefinedNames`

### 4. example_generated

- **Target:** `bitflags.ExampleGenerated [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 14)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

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
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |

