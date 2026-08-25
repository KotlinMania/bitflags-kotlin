// port-lint: tests src/tests/unknown_bits.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class UnknownBitsTest {
    @Test
    public fun cases() {
        case(0uL, TestFlags.empty())
        case(0uL, TestFlags.A)
        case(0uL, TestFlags.all())
        case(1uL shl 3, TestFlags.ABC or TestFlags.fromBitsRetain(1uL shl 3))
        case(1uL shl 3, TestFlags.fromBitsRetain(1uL shl 3))
        case(0b11111000uL, TestFlags.fromBitsRetain(0b11111000uL))
        case(0uL, TestZero.empty())
        case(0uL, TestExternal.fromBitsRetain(0xFFuL))
    }

    private fun <T : BitFlags<T>> case(expected: ULong, value: T) {
        assertEquals(expected, value.unknownBits(), "$value.unknownBits()")
    }
}
