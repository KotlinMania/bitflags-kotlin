// port-lint: tests src/tests/from_name.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class FromNameTest {
    @Test
    public fun cases() {
        case(1uL, "A", TestFlags.fromName("A"))
        case(1uL shl 1, "B", TestFlags.fromName("B"))
        case(1uL or (1uL shl 1) or (1uL shl 2), "ABC", TestFlags.fromName("ABC"))

        case(null, "", TestFlags.fromName(""))
        case(null, "a", TestFlags.fromName("a"))
        case(null, "0x1", TestFlags.fromName("0x1"))
        case(null, "A | B", TestFlags.fromName("A | B"))

        case(0uL, "ZERO", TestZero.fromName("ZERO"))
        case(2uL, "二", TestUnicode.fromName("二"))

        case(null, "_", TestExternal.fromName("_"))
        case(null, "", TestExternal.fromName(""))
    }

    private fun <T : BitFlags<T>> case(expected: ULong?, input: String, inherent: T?) {
        assertEquals(expected, inherent?.bits(), "T::fromName($input)")
    }
}
