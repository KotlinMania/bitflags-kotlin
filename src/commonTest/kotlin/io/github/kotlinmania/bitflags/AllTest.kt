// port-lint: tests src/tests/all.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class AllTest {
    @Test
    public fun cases() {
        case(1uL or (1uL shl 1) or (1uL shl 2), TestFlags.all())
        case(0uL, TestZero.all())
        case(0uL, TestEmpty.all())
        case(0xFFuL, TestExternal.all())
    }

    private fun <T : BitFlags<T>> case(expected: ULong, flags: T) {
        assertEquals(expected, flags.bits(), "T::all()")
        assertEquals(expected, flags.all().bits(), "Flags::all()")
    }
}
