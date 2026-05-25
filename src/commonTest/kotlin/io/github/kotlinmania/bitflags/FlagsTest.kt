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
        val named = value.iterNames().asSequence().map { it.name to it.flag.bits() }.toList()
        val all = value.iter().asSequence().map { it.bits() }.toList()

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
}
