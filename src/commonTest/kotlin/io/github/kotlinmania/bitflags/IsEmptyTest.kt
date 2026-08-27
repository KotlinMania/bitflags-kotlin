// port-lint: tests src/tests/is_empty.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class IsEmptyTest {
    @Test
    public fun cases() {
        case(true, TestFlags.empty())
        case(false, TestFlags.A)
        case(false, TestFlags.ABC)
        case(false, TestFlags.ABC or TestFlags.fromBitsRetain(1uL shl 3))
        case(true, TestZero.empty())
        case(true, TestEmpty.empty())
    }

    private fun <T : BitFlags<T>> case(expected: Boolean, value: T) {
        assertEquals(expected, value.isEmpty(), "$value.isEmpty()")
    }
}
