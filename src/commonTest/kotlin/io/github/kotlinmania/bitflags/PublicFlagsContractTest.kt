package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

public class ManualFlags(bits: ULong) : Flags<ManualFlags>(bits) {
    override fun flags(): List<Flag<ManualFlags>> = listOf(
        Flag.new("A", ManualFlags(1uL)),
        Flag.new("B", ManualFlags(2uL)),
    )

    override fun fromBitsRetain(bits: ULong): ManualFlags = ManualFlags(bits)
}

class PublicFlagsContractTest {
    @Test
    fun manualFlagsRetainUnknownBitsAndUseGenericOperations() {
        val value = ManualFlags(11uL)
        assertEquals(3uL, value.knownBits())
        assertEquals(8uL, value.unknownBits())
        assertTrue(value.contains(ManualFlags(1uL)))
        assertNull(value.fromBits(8uL))
        assertEquals(3uL, value.fromBitsTruncate(11uL).bits())
        assertEquals(listOf(1uL, 2uL, 8uL), value.iter().asSequence().map { it.bits() }.toList())
        assertEquals("A | B | 0x8", toText(value))
        assertEquals(11uL, fromStr("A | B | 0x8", ManualFlags(0uL)).bits())
    }

    @Test
    fun legacyBitFlagsImplementsCanonicalFlagsContract() {
        val value: Flags<ExampleFlags> = ExampleFlags.A
        val legacy: BitFlags<ExampleFlags> = ExampleFlags.A
        val marker: ImplementedByBitFlagsMacro = legacy
        assertEquals(1uL, value.bits())
        assertTrue(marker === legacy)
    }

    @Test
    fun flagDefinitionDoesNotRequireFlagsValue() {
        val definition = Flag.new("label", "value")
        assertEquals("label", definition.name())
        assertEquals("value", definition.value())
    }

    @Test
    fun generatedNamedIteratorPreservesDefinitionsAndRemainingState() {
        val source = ManualFlags(11uL)
        val iterator = IterNames.privateConstNew(
            listOf(Flag.new("SECOND", ManualFlags(2uL))),
            source,
            ManualFlags(10uL),
        )
        assertEquals(10uL, iterator.remaining().bits())
        val flag = iterator.next()
        assertEquals("SECOND", flag.name)
        assertEquals(2uL, flag.flag.bits())
        assertFalse(iterator.hasNext())
        assertEquals(8uL, iterator.remaining().bits())
    }

    @Test
    fun generatedIteratorYieldsOnlySuppliedRemainingBits() {
        val iterator = Iter.privateConstNew(
            listOf(Flag.new("SECOND", ManualFlags(2uL))),
            ManualFlags(11uL),
            ManualFlags(10uL),
        )
        assertEquals(listOf(2uL, 8uL), iterator.asSequence().map { it.bits() }.toList())
        assertFalse(iterator.hasNext())
    }

    @Test
    fun generatedIteratorDoesNotYieldDefinitionAbsentFromSource() {
        val iterator = Iter.privateConstNew(
            listOf(Flag.new("ABSENT", ManualFlags(4uL))),
            ManualFlags(3uL),
            ManualFlags(7uL),
        )
        assertEquals(listOf(7uL), iterator.asSequence().map { it.bits() }.toList())
    }
}
