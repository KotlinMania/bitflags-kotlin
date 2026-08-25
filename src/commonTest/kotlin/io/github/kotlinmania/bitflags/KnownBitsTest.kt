// port-lint: tests src/tests/known_bits.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class KnownBitsTest {
    @Test
    public fun cases() {
        case(0uL, TestFlags.empty())
        case(1uL, TestFlags.A)
        case(1uL or (1uL shl 1) or (1uL shl 2), TestFlags.all())
        case(1uL or (1uL shl 1) or (1uL shl 2), TestFlags.ABC or TestFlags.fromBitsRetain(1uL shl 3))
        case(0uL, TestFlags.fromBitsRetain(1uL shl 3))
        case(0uL, TestZero.empty())
        case(0xFFuL, TestExternal.fromBitsRetain(0xFFuL))
    }

    private fun <T : BitFlags<T>> case(expected: ULong, value: T) {
        assertEquals(expected, value.knownBits(), "$value.knownBits()")
    }
}
