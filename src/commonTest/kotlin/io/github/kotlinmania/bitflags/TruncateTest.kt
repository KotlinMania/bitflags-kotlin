// port-lint: tests src/tests/truncate.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class TruncateTest {
    @Test
    public fun cases() {
        case(
            TestFlags.ABC or TestFlags.fromBitsRetain(1uL shl 3),
            TestFlags.ABC,
        )
        case(TestZero.empty(), TestZero.empty())
        case(TestZero.all(), TestZero.all())
        case(
            TestFlags.fromBitsRetain(1uL shl 3) or TestFlags.all(),
            TestFlags.all(),
        )
    }

    private fun <T : BitFlags<T>> case(before: T, after: T) {
        assertEquals(after, before.truncate(), "$before.truncate()")
    }
}
