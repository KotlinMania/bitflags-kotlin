// port-lint: tests bitflags/src/src/tests/from_bits.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class FromBitsTest {
    @Test
    public fun cases() {
        case(0uL, 0uL, TestFlags.fromBits(0uL))
        case(1uL, 1uL, TestFlags.fromBits(1uL))
        case(
            1uL or (1uL shl 1) or (1uL shl 2),
            1uL or (1uL shl 1) or (1uL shl 2),
            TestFlags.fromBits(1uL or (1uL shl 1) or (1uL shl 2))
        )
        case(null, 1uL shl 3, TestFlags.fromBits(1uL shl 3))
        case(null, 1uL or (1uL shl 3), TestFlags.fromBits(1uL or (1uL shl 3)))
        case(1uL or (1uL shl 1), 1uL or (1uL shl 1), TestOverlapping.fromBits(1uL or (1uL shl 1)))
        case(1uL shl 1, 1uL shl 1, TestOverlapping.fromBits(1uL shl 1))
        case(1uL shl 5, 1uL shl 5, TestExternal.fromBits(1uL shl 5))
    }

    private fun <T : BitFlags<T>> case(expected: ULong?, input: ULong, inherent: T?) {
        assertEquals(expected, inherent?.bits(), "T::fromBits($input)")
    }
}
