// port-lint: tests tests/is_all.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class IsAllTest {
    @Test
    public fun cases() {
        case(false, TestFlags.empty())
        case(false, TestFlags.A)
        case(true, TestFlags.ABC)
        case(true, TestFlags.ABC or TestFlags.fromBitsRetain(1uL shl 3))
        case(true, TestZero.empty())
        case(true, TestEmpty.empty())
    }

    private fun <T : BitFlags<T>> case(expected: Boolean, value: T) {
        assertEquals(expected, value.isAll(), "$value.isAll()")
    }
}
