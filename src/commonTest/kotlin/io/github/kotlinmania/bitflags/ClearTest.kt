// port-lint: tests tests/clear.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class ClearTest {
    @Test
    public fun cases() {
        case(TestFlags.fromBitsRetain(0uL))
        case(TestFlags.fromBitsRetain(1uL shl 3))
        case(TestFlags.ABC or TestFlags.fromBitsRetain(1uL shl 3))
        case(TestZero.empty())
        case(TestZero.all())
        case(TestFlags.fromBitsRetain(1uL shl 3) or TestFlags.all())
    }

    private fun <T : BitFlags<T>> case(flags: T) {
        val cleared = flags.clear()
        assertEquals(flags.empty(), cleared, "$flags.clear()")
    }
}
