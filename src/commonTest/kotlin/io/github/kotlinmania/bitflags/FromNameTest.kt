// port-lint: tests tests/basic.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FromNameTest {
    @Test
    fun testFromNameLookup() {
        val a = ExampleFlags.empty().fromName("A")
        assertEquals(ExampleFlags.A, a)

        val b = ExampleFlags.empty().fromName("B")
        assertEquals(ExampleFlags.B, b)

        val c = ExampleFlags.empty().fromName("C")
        assertEquals(ExampleFlags.C, c)

        val abc = ExampleFlags.empty().fromName("ABC")
        assertEquals(ExampleFlags.ABC, abc)

        val missing = ExampleFlags.empty().fromName("NON_EXISTENT")
        assertNull(missing)

        val emptyName = ExampleFlags.empty().fromName("")
        assertNull(emptyName)
    }
}
