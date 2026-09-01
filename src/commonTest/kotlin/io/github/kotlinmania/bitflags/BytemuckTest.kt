// port-lint: tests external/bytemuck.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class BytemuckTest {
    @Test
    public fun testBytemuck() {
        val color = ExampleFlags.A
        assertEquals(1uL, color.bits())
    }
}
