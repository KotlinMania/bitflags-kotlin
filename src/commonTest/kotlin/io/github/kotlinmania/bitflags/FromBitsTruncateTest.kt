// port-lint: tests bitflags/src/src/tests/from_bits_truncate.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class FromBitsTruncateTest {
    @Test
    public fun cases() {
        case(0uL, 0uL, TestFlags.fromBitsTruncate(0uL))
        case(1uL, 1uL, TestFlags.fromBitsTruncate(1uL))
        case(
            1uL or (1uL shl 1) or (1uL shl 2),
            1uL or (1uL shl 1) or (1uL shl 2),
            TestFlags.fromBitsTruncate(1uL or (1uL shl 1) or (1uL shl 2))
        )
        case(0uL, 1uL shl 3, TestFlags.fromBitsTruncate(1uL shl 3))
        case(1uL, 1uL or (1uL shl 3), TestFlags.fromBitsTruncate(1uL or (1uL shl 3)))
        case(1uL or (1uL shl 1), 1uL or (1uL shl 1), TestOverlapping.fromBitsTruncate(1uL or (1uL shl 1)))
        case(1uL shl 1, 1uL shl 1, TestOverlapping.fromBitsTruncate(1uL shl 1))
        case(1uL shl 5, 1uL shl 5, TestExternal.fromBitsTruncate(1uL shl 5))
    }

    private fun <T : BitFlags<T>> case(expected: ULong, input: ULong, inherent: T) {
        assertEquals(expected, inherent.bits(), "T::fromBitsTruncate($input)")
    }
}
