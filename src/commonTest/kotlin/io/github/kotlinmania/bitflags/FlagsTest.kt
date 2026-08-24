// port-lint: tests tests/flags.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FlagsTest {
    @Test
    fun flagsDefinitionsMatchUpstreamOrder() {
        val flags = ExampleFlags.FLAGS.map { it.name() to it.value().bits() }

        assertEquals(
            listOf(
                "A" to 1uL,
                "B" to (1uL shl 1),
                "C" to (1uL shl 2),
                "ABC" to (1uL or (1uL shl 1) or (1uL shl 2)),
            ),
            flags,
        )

        assertEquals(0, emptyList<Flag<ExampleFlags>>().count())
    }

    @Test
    fun externalDefinitionsKeepUnnamedCatchAllFlag() {
        val flags = ExampleExternalFlags.FLAGS.map { it.name() to it.value().bits() }

        assertEquals(
            listOf(
                "A" to 1uL,
                "B" to (1uL shl 1),
                "C" to (1uL shl 2),
                "ABC" to (1uL or (1uL shl 1) or (1uL shl 2)),
                "" to ULong.MAX_VALUE,
            ),
            flags,
        )
    }

    @Test
    fun conversionAndContainmentFollowBitflagsRules() {
        val value = ExampleFlags.A.union(ExampleFlags.C)

        assertEquals(0b101uL, value.bits())
        assertTrue(value.contains(ExampleFlags.A))
        assertFalse(value.contains(ExampleFlags.B))
        assertTrue(value.intersects(ExampleFlags.C))
        assertEquals(ExampleFlags.A, value.difference(ExampleFlags.C))
        assertEquals(ExampleFlags.B, ExampleFlags.fromBitsTruncate(0b111uL).difference(value))
        assertNull(ExampleFlags.fromBits(0b1000uL))
        assertEquals(0uL, ExampleFlags.fromBitsTruncate(0b1000uL).bits())
        assertEquals(0b1000uL, ExampleFlags.fromBitsRetain(0b1000uL).bits())
    }

    @Test
    fun iteratorsYieldNamedFlagsThenRemainingBits() {
        val value = ExampleFlags.fromBitsRetain(0b1011uL)
        val named =
            value
                .iterNames()
                .asSequence()
                .map { it.name to it.flag.bits() }
                .toList()
        val all =
            value
                .iter()
                .asSequence()
                .map { it.bits() }
                .toList()

        assertEquals(listOf("A" to 1uL, "B" to 2uL), named)
        assertEquals(listOf(1uL, 2uL, 0b1000uL), all)
    }

    @Test
    fun textParserRetainsUnknownBitsUnlessTruncated() {
        val parsed = fromText("A | B | 0x8", ExampleFlags.empty())

        assertEquals(0b1011uL, parsed.bits())
        assertEquals("A | B | 0x8", toText(parsed))
        assertEquals("A | B", toTextTruncate(parsed))
        assertEquals(0b011uL, fromTextTruncate("A | B | 0x8", ExampleFlags.empty()).bits())
    }

    @Test
    fun strictParserRejectsHexAndUnknownNames() {
        assertEquals(0b011uL, fromTextStrict("A | B", ExampleFlags.empty()).bits())

        assertFailsWith<IllegalArgumentException> {
            fromTextStrict("A | 0x8", ExampleFlags.empty())
        }
        assertFailsWith<IllegalArgumentException> {
            fromText("missing", ExampleFlags.empty())
        }
    }

    @Test
    fun bitwiseOperationsAndMutations() {
        val a = ExampleFlags.A
        val b = ExampleFlags.B
        val c = ExampleFlags.C

        assertEquals(0b011uL, a.insert(b).bits())
        assertEquals(
            0b001uL,
            ExampleFlags.ABC
                .remove(b)
                .remove(c)
                .bits(),
        )
        assertEquals(0b010uL, a.toggle(ExampleFlags.fromBitsRetain(0b011uL)).bits())
        assertEquals(0b011uL, a.set(b, true).bits())
        assertEquals(0b001uL, a.set(b, false).bits())
        assertEquals(0uL, a.clear().bits())
        assertTrue(a.clear().isEmpty())
        assertTrue(ExampleFlags.empty().isEmpty())
        assertTrue(ExampleFlags.all().isAll())
        assertFalse(a.isAll())

        assertEquals(0b001uL, a.intersection(ExampleFlags.ABC).bits())
        assertEquals(0b011uL, a.union(b).bits())
        assertEquals(0b010uL, ExampleFlags.ABC.difference(a.union(c)).bits())
        assertEquals(0b011uL, a.symmetricDifference(b).bits())
        assertEquals(0b110uL, a.complement().bits())
    }

    @Test
    fun knownAndUnknownBits() {
        val flagWithUnknown = ExampleFlags.fromBitsRetain(0b1001uL)
        assertEquals(0b0001uL, flagWithUnknown.knownBits())
        assertEquals(0b1000uL, flagWithUnknown.unknownBits())
        assertTrue(flagWithUnknown.containsUnknownBits())
        assertFalse(ExampleFlags.A.containsUnknownBits())
        assertEquals(ExampleFlags.A, flagWithUnknown.truncate())
    }

    @Test
    fun flagConstructorsAndProperties() {
        val flag = Flag.new("TEST", ExampleFlags.A)
        assertEquals("TEST", flag.name())
        assertEquals(ExampleFlags.A, flag.value())
        assertTrue(flag.isNamed())
        assertFalse(flag.isUnnamed())

        val unnamed = Flag.new("", ExampleFlags.A)
        assertFalse(unnamed.isNamed())
        assertTrue(unnamed.isUnnamed())
    }

    @Test
    fun iteratorFactoriesAndConstructors() {
        val flags = ExampleFlags.fromBitsRetain(0b1011uL)
        val iter = Iter.new(flags)
        val collected = mutableListOf<ULong>()
        while (iter.hasNext()) {
            collected.add(iter.next().bits())
        }
        assertEquals(listOf(1uL, 2uL, 0b1000uL), collected)

        val definedIter = IterDefinedNames.new(ExampleFlags.empty())
        val names = mutableListOf<String>()
        while (definedIter.hasNext()) {
            names.add(definedIter.next().name)
        }
        assertEquals(listOf("A", "B", "C", "ABC"), names)
    }

    @Test
    fun asDisplayAndWriterFunctions() {
        val value = ExampleFlags.A.union(ExampleFlags.B)
        val display = AsDisplay(value)
        val sb = StringBuilder()
        display.fmt(sb)
        assertEquals("A | B", sb.toString())
        assertEquals("A | B", display.toString())

        val strictSb = StringBuilder()
        toWriterStrict(value, strictSb)
        assertEquals("A | B", strictSb.toString())

        val truncSb = StringBuilder()
        toWriterTruncate(ExampleFlags.fromBitsRetain(0b1011uL), truncSb)
        assertEquals("A | B", truncSb.toString())
    }

    @Test
    fun parseErrorConstructors() {
        val err1 = ParseError.emptyFlag()
        assertEquals("encountered empty flag", err1.toString())

        val err2 = ParseError.invalidNamedFlag("FOO")
        assertEquals("unrecognized named flag `FOO`", err2.toString())

        val err3 = ParseError.invalidHexFlag("xyz")
        assertEquals("invalid hex flag `xyz`", err3.toString())
    }
}
