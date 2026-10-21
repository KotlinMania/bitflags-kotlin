import Testing
import Bitflags

@Suite("Bitflags exported behavior")
struct BitflagsExportTests {
    @Test func swiftModuleLoads() {
        let kind = io.github.kotlinmania.bitflags.ParseErrorKind.EMPTY_FLAG
        #expect(kind.description == "EMPTY_FLAG")
    }

    @Test func typedFlagsRetainKnownAndUnknownBits() {
        let value: io.github.kotlinmania.bitflags.ExampleFlags =
            io.github.kotlinmania.bitflags.ExampleFlags.Companion.shared.fromBitsRetain(bits: 11)
        #expect(value.bits() == 11)
        #expect(value.knownBits() == 3)
        #expect(value.unknownBits() == 8)
        #expect(value.containsUnknownBits())
        #expect(value.fromBits(bits: 8) == nil)
        #expect(value.fromBitsTruncate(bits: 11).bits() == 3)
    }

    @Test func typedFlagsOperations() {
        let a: io.github.kotlinmania.bitflags.ExampleFlags =
            io.github.kotlinmania.bitflags.ExampleFlags.Companion.shared.A
        let b: io.github.kotlinmania.bitflags.ExampleFlags =
            io.github.kotlinmania.bitflags.ExampleFlags.Companion.shared.B
        let union: io.github.kotlinmania.bitflags.ExampleFlags = a.union(other: b)
        #expect(union.bits() == 3)
        #expect(union.contains(other: a))
        #expect(union.difference(other: b).bits() == 1)
        #expect(a.bits() == 1)
    }
}
