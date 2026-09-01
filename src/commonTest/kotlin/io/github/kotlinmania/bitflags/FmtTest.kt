// port-lint: tests tests/fmt.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class FmtTest {
    @Test
    public fun cases() {
        assertEquals("TestFlags(0x0)", TestFlags.empty().toString())
        assertEquals("TestFlags(A)", TestFlags.A.toString())
        assertEquals("TestFlags(A | B | C)", TestFlags.all().toString())
        assertEquals("TestFlags(0x8)", TestFlags.fromBitsRetain(1uL shl 3).toString())
        assertEquals("TestFlags(A | 0x8)", (TestFlags.A or TestFlags.fromBitsRetain(1uL shl 3)).toString())
        assertEquals("TestZero(0x0)", TestZero.ZERO.toString())
        assertEquals("TestZero(0x1)", (TestZero.ZERO or TestZero.fromBitsRetain(1uL)).toString())
        assertEquals("TestZeroOne(ONE)", TestZeroOne.ONE.toString())
        assertEquals("TestOverlapping(0x2)", TestOverlapping.fromBitsRetain(1uL shl 1).toString())
        assertEquals("TestExternal(A | B | 0x8)", TestExternal.fromBitsRetain(1uL or (1uL shl 1) or (1uL shl 3)).toString())
        assertEquals("TestExternal(A | B | C | 0xf8)", TestExternal.all().toString())
        assertEquals("TestExternalFull(0xff)", TestExternalFull.all().toString())
    }
}
