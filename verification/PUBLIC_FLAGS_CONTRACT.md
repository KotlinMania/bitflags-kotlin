# Public Flags contract

The pinned input is tmp/bitflags at the revision and hashes recorded with the build receipt.
Its traits.rs defines Flags as the primary generic contract and the deprecated BitFlags
as a separate trait extending Flags and ImplementedByBitFlagsMacro. Kotlin now has
actual declarations for both names. BitFlags is not a type alias or a replacement noun.

| Previous declaration | Actual declaration | Compatibility |
|---|---|---|
| BitFlags owns shared operations | Flags owns operations; legacy BitFlags extends it | Changed superclass and binary owner; rebuild compiled callers |
| Iter alias of FlagIterator | Iter class | Source callers of FlagIterator must use Iter; binary break |
| IterNames alias of NamedFlagIterator | IterNames class | Source callers of NamedFlagIterator must use IterNames; binary break |
| DefinedNamedFlagIterator | IterDefinedNames | Canonical upstream noun; source/binary break |
| Item alias | NamedFlag record used directly | Removed alias; callers use actual record |
| generation factory with underscored Rust spelling | privateConstNew | Kotlin naming convention; source/binary break |
| hidden producer superclass and signature types | visible public declarations | Native caller subclasses can compile without hiding their API |

All operation algorithms remain in their owning implementation. The generation factory
now retains its actual definition list, original source bits and remaining bits; it no
longer restarts from the source value. Tests include definitions differing from the
normal flags and remaining bits differing from source.

The existing integer representation is ULong. This does not implement Rust's associated
Bits type for every integer width, nor unsigned 128-bit storage. Existing functional
insert/remove/set return new values instead of mutating Rust Copy variables. These are
real compatibility limits, not evidence of complete trait parity. No UShort-shaped
stand-in is introduced for Ratatui, and no consumer pin changes to unpublished output.

RUST_CALLERS.md identifies crossterm event flags, rama-http classification flags and
Ratatui core Modifier/widgets borders as upstream macro callers. Ratatui is the current
native failure witness. Its published BitFlags pin remains unchanged until producer
publication and immutable artifact verification. In this producer, TestTypes and
FlagsTest were migrated to the actual iterator names. Independent public consumer-owned
Flags and legacy BitFlags subclasses compile against staged coordinates on Android/JVM
and macOS ARM64; source preparation alone is not a successful compile result.

The compiler plugin catalog already selects Kotlin 2.4.20. The stale versions.kotlin
property used by the Swift export worker is aligned to that same stock version;
no experimental exporter is selected. The inherited bridge Werror exception, generated
API deletion and Swift tools downgrade are removed. Swift 6 and warnings-as-errors
apply to generated dependency modules and the real typed-value harness. The root test
includes allTests, explicit host tests and that Swift harness.
