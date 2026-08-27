// port-lint: tests bitflags/src/src/tests/bits.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class BitsTest {
    @Test
    public fun cases() {
        case(0uL, TestFlags.empty())
        case(1uL, TestFlags.A)
        case(1uL or (1uL shl 1) or (1uL shl 2), TestFlags.ABC)
        case(0xFFuL, TestFlags.fromBitsRetain(0xFFuL))
        case(1uL shl 3, TestFlags.fromBitsRetain(1uL shl 3))
        case(1uL shl 3, TestZero.fromBitsRetain(1uL shl 3))
        case(1uL shl 3, TestEmpty.fromBitsRetain(1uL shl 3))
        case((1uL shl 4) or (1uL shl 6), TestExternal.fromBitsRetain((1uL shl 4) or (1uL shl 6)))
    }

    private fun <T : BitFlags<T>> case(expected: ULong, flags: T) {
        assertEquals(expected, flags.bits(), "$flags.bits()")
    }
}
