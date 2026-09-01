// port-lint: tests tests/extend.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class ExtendTest {
    @Test
    public fun cases() {
        var flags = TestFlags.empty()
        flags = flags.union(TestFlags.A)
        assertEquals(TestFlags.A, flags)

        flags = flags.union(TestFlags.A or TestFlags.B or TestFlags.C)
        assertEquals(TestFlags.ABC, flags)

        flags = flags.union(TestFlags.fromBitsRetain(1uL shl 5))
        assertEquals(TestFlags.ABC or TestFlags.fromBitsRetain(1uL shl 5), flags)
    }

    public class ExternalTest {
        @Test
        public fun cases() {
            var flags = TestExternal.empty()
            flags = flags.union(TestExternal.A)
            assertEquals(TestExternal.A, flags)

            flags = flags.union(TestExternal.A or TestExternal.B or TestExternal.C)
            assertEquals(TestExternal.ABC, flags)

            flags = flags.union(TestExternal.fromBitsRetain(1uL shl 5))
            assertEquals(TestExternal.ABC or TestExternal.fromBitsRetain(1uL shl 5), flags)
        }
    }
}
