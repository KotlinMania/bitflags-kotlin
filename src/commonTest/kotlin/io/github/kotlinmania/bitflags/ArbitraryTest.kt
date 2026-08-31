// port-lint: tests bitflags/src/external/arbitrary.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class ArbitraryTest {
    @Test
    public fun testArbitrary() {
        val color = ExampleFlags.fromBits(1uL)
        assertEquals(ExampleFlags.A, color)
    }
}
