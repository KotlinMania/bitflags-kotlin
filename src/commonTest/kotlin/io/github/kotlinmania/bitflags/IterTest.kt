// port-lint: tests bitflags/src/iter.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

public class IterTest {
    @Test
    public fun cases() {
        caseIter(emptyList(), TestFlags.empty())
        caseIter(listOf(1uL), TestFlags.A)
        caseIter(listOf(1uL, 1uL shl 1), TestFlags.A or TestFlags.B)
        caseIter(
            listOf(1uL, 1uL shl 1, 1uL shl 3),
            TestFlags.A or TestFlags.B or TestFlags.fromBitsRetain(1uL shl 3)
        )
        caseIter(listOf(1uL, 1uL shl 1, 1uL shl 2), TestFlags.ABC)
        caseIter(
            listOf(1uL, 1uL shl 1, 1uL shl 2, 1uL shl 3),
            TestFlags.ABC or TestFlags.fromBitsRetain(1uL shl 3)
        )
        caseIter(
            listOf(1uL or (1uL shl 1) or (1uL shl 2)),
            TestFlagsInvert.ABC
        )
        caseIter(emptyList(), TestZero.ZERO)
        caseIter(
            listOf(1uL, 1uL shl 1, 1uL shl 2, 0b11111000uL),
            TestExternal.all()
        )

        // iterNames
        caseIterNames(emptyList(), TestFlags.empty())
        caseIterNames(listOf(Pair("A", 1uL)), TestFlags.A)
        caseIterNames(listOf(Pair("A", 1uL), Pair("B", 1uL shl 1)), TestFlags.A or TestFlags.B)
        caseIterNames(
            listOf(Pair("A", 1uL), Pair("B", 1uL shl 1)),
            TestFlags.A or TestFlags.B or TestFlags.fromBitsRetain(1uL shl 3)
        )
        caseIterNames(
            listOf(Pair("A", 1uL), Pair("B", 1uL shl 1), Pair("C", 1uL shl 2)),
            TestFlags.ABC
        )
        caseIterNames(
            listOf(Pair("A", 1uL), Pair("B", 1uL shl 1), Pair("C", 1uL shl 2)),
            TestFlags.ABC or TestFlags.fromBitsRetain(1uL shl 3)
        )
        caseIterNames(
            listOf(Pair("ABC", 1uL or (1uL shl 1) or (1uL shl 2))),
            TestFlagsInvert.ABC
        )
        caseIterNames(emptyList(), TestZero.ZERO)
        caseIterNames(listOf(Pair("A", 1uL)), TestOverlappingFull.A)
        caseIterNames(
            listOf(Pair("A", 1uL), Pair("D", 1uL shl 1)),
            TestOverlappingFull.A or TestOverlappingFull.D
        )
    }

    private fun <T : BitFlags<T>> caseIter(expected: List<ULong>, value: T) {
        val result = mutableListOf<ULong>()
        val it = value.iter()
        while (it.hasNext()) {
            result.add(it.next().bits())
        }
        assertEquals(expected, result, "$value.iter()")
    }

    private fun <T : BitFlags<T>> caseIterNames(expected: List<Pair<String, ULong>>, value: T) {
        val result = mutableListOf<Pair<String, ULong>>()
        val it = value.iterNames()
        while (it.hasNext()) {
            val item = it.next()
            result.add(Pair(item.name, item.flag.bits()))
        }
        assertEquals(expected, result, "$value.iterNames()")
    }

    @Test
    public fun testDefinedNames() {
        val allNamed = TestFlags.FLAGS.filter { it.isNamed() }.map { Pair(it.name(), it.value().bits()) }
        val expected = listOf(
            Pair("A", 1uL),
            Pair("B", 1uL shl 1),
            Pair("C", 1uL shl 2),
            Pair("ABC", 1uL or (1uL shl 1) or (1uL shl 2)),
        )
        assertEquals(expected.size, allNamed.size)
        for (flag in expected) {
            assertTrue(allNamed.contains(flag), "Missing flag $flag")
        }
    }
}
