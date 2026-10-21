package consumer

import io.github.kotlinmania.bitflags.Iter
import io.github.kotlinmania.bitflags.IterNames
import io.github.kotlinmania.bitflags.fromStr
import io.github.kotlinmania.bitflags.toText
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class FlagsConsumerTest {
    @Test
    fun publicSubclassUsesCanonicalOperations() {
        val value = ConsumerFlags(11uL)
        assertEquals(3uL, value.knownBits())
        assertEquals(8uL, value.unknownBits())
        assertEquals(3uL, value.fromBitsTruncate(11uL).bits())
        assertNull(value.fromBits(8uL))
        assertEquals(listOf(1uL, 2uL, 8uL), value.iter().asSequence().map { it.bits() }.toList())
    }

    @Test
    fun legacyPublicSubclassRetainsUnknownBits() {
        val value = LegacyConsumerFlags(9uL)
        assertEquals(1uL, value.knownBits())
        assertEquals(8uL, value.unknownBits())
        assertEquals(9uL, value.fromBitsRetain(9uL).bits())
    }

    @Test
    fun canonicalFormattingAndParsingRoundTrip() {
        val value = fromStr("A | B | 0x8", ConsumerFlags(0uL))
        assertEquals(11uL, value.bits())
        assertEquals("A | B | 0x8", toText(value))
    }

    @Test
    fun generatedIteratorStateIsPreservedAcrossPublication() {
        val value = ConsumerFlags(11uL)
        val names = IterNames.privateConstNew(value.flags().take(1), value, ConsumerFlags(9uL))
        assertEquals("A", names.next().name)
        assertFalse(names.hasNext())
        assertEquals(8uL, names.remaining().bits())
        val values = Iter.privateConstNew(value.flags().take(1), value, ConsumerFlags(9uL))
        assertEquals(listOf(1uL, 8uL), values.asSequence().map { it.bits() }.toList())
    }
}
