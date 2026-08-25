// port-lint: tests src/tests/complement.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class ComplementTest {
    @Test
    public fun cases() {
        case(0uL, TestFlags.all())
        case(0uL, TestFlags.fromBitsRetain(0xFFuL))
        case(1uL or (1uL shl 1), TestFlags.C)
        case(1uL or (1uL shl 1), TestFlags.C or TestFlags.fromBitsRetain(1uL shl 3))
        case(1uL or (1uL shl 1) or (1uL shl 2), TestFlags.empty())
        case(1uL or (1uL shl 1) or (1uL shl 2), TestFlags.fromBitsRetain(1uL shl 3))
        case(0uL, TestZero.empty())
        case(0uL, TestEmpty.empty())
        case(1uL shl 2, TestOverlapping.AB)
        case(0xFFuL, TestExternal.empty())
    }

    private fun <T : BitFlags<T>> case(expected: ULong, value: T) {
        assertEquals(expected, value.complement().bits(), "$value.complement()")
        assertEquals(expected, (!value).bits(), "!$value")
    }
}
