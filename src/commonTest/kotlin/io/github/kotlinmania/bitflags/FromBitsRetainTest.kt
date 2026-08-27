// port-lint: tests bitflags/src/src/tests/from_bits_retain.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class FromBitsRetainTest {
    @Test
    public fun cases() {
        case(0uL, TestFlags.fromBitsRetain(0uL))
        case(1uL, TestFlags.fromBitsRetain(1uL))
        case(1uL or (1uL shl 1) or (1uL shl 2), TestFlags.fromBitsRetain(1uL or (1uL shl 1) or (1uL shl 2)))
        case(1uL shl 3, TestFlags.fromBitsRetain(1uL shl 3))
        case(1uL or (1uL shl 3), TestFlags.fromBitsRetain(1uL or (1uL shl 3)))
        case(1uL or (1uL shl 1), TestOverlapping.fromBitsRetain(1uL or (1uL shl 1)))
        case(1uL shl 1, TestOverlapping.fromBitsRetain(1uL shl 1))
        case(1uL shl 5, TestExternal.fromBitsRetain(1uL shl 5))
    }

    private fun <T : BitFlags<T>> case(input: ULong, inherent: T) {
        assertEquals(input, inherent.bits(), "T::fromBitsRetain($input)")
    }
}
