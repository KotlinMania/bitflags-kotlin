// port-lint: tests bitflags/src/external/serde.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class SerdeTest {
    @Test
    public fun testSerdeBitflagsDefault() {
        assertEquals("", toText(ExampleFlags.empty()))
        assertEquals(0uL, ExampleFlags.empty().bits())
        assertEquals("A | B", toText(ExampleFlags.A or ExampleFlags.B))
        assertEquals(3uL, (ExampleFlags.A or ExampleFlags.B).bits())
    }
}
