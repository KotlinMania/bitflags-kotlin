// port-lint: tests src/tests/unknown.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class UnknownTest {
    @Test
    public fun cases() {
        case(false, TestFlags.empty())
        case(false, TestFlags.A)
        case(true, TestFlags.ABC or TestFlags.fromBitsRetain(1uL shl 3))
        case(true, TestFlags.empty() or TestFlags.fromBitsRetain(1uL shl 3))
        case(false, TestFlags.all())
        case(false, TestZero.empty())
    }

    private fun <T : BitFlags<T>> case(expected: Boolean, value: T) {
        assertEquals(expected, value.containsUnknownBits(), "$value.containsUnknownBits()")
    }
}
