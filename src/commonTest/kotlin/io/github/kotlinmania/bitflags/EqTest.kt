// port-lint: tests src/tests/eq.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

public class EqTest {
    @Test
    public fun cases() {
        assertEquals(TestFlags.empty(), TestFlags.empty())
        assertEquals(TestFlags.all(), TestFlags.all())

        assertTrue(TestFlags.fromBitsRetain(1uL) < TestFlags.fromBitsRetain(2uL))
        assertTrue(TestFlags.fromBitsRetain(2uL) > TestFlags.fromBitsRetain(1uL))
    }
}
