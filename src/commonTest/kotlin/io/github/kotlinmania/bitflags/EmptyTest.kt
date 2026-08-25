// port-lint: tests src/tests/empty.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class EmptyTest {
    @Test
    public fun cases() {
        case(0uL, TestFlags.empty())
        case(0uL, TestZero.empty())
        case(0uL, TestEmpty.empty())
        case(0uL, TestExternal.empty())
    }

    private fun <T : BitFlags<T>> case(expected: ULong, flags: T) {
        assertEquals(expected, flags.bits(), "T::empty()")
        assertEquals(expected, flags.empty().bits(), "Flags::empty()")
    }
}
