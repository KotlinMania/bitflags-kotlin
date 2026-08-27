// port-lint: tests parser.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

public class ParserTest {
    @Test
    public fun valid() {
        assertEquals(0uL, fromStr("", TestFlags.empty()).bits())
        assertEquals(1uL, fromStr("A", TestFlags.empty()).bits())
        assertEquals(1uL, fromStr(" A ", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1) or (1uL shl 2), fromStr("A | B | C", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1) or (1uL shl 2), fromStr("A\n|\tB\r\n|   C ", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1) or (1uL shl 2), fromStr("A|B|C", TestFlags.empty()).bits())
        assertEquals(1uL shl 3, fromStr("0x8", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 3), fromStr("A | 0x8", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1) or (1uL shl 3), fromStr("0x1 | 0x8 | B", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1), fromStr("一 | 二", TestUnicode.empty()).bits())

        // fromStrTruncate
        assertEquals(0uL, fromStrTruncate("", TestFlags.empty()).bits())
        assertEquals(1uL, fromStrTruncate("A", TestFlags.empty()).bits())
        assertEquals(1uL, fromStrTruncate(" A ", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1) or (1uL shl 2), fromStrTruncate("A | B | C", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1) or (1uL shl 2), fromStrTruncate("A\n|\tB\r\n|   C ", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1) or (1uL shl 2), fromStrTruncate("A|B|C", TestFlags.empty()).bits())
        assertEquals(0uL, fromStrTruncate("0x8", TestFlags.empty()).bits())
        assertEquals(1uL, fromStrTruncate("A | 0x8", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1), fromStrTruncate("0x1 | 0x8 | B", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1), fromStrTruncate("一 | 二", TestUnicode.empty()).bits())

        // fromStrStrict
        assertEquals(0uL, fromStrStrict("", TestFlags.empty()).bits())
        assertEquals(1uL, fromStrStrict("A", TestFlags.empty()).bits())
        assertEquals(1uL, fromStrStrict(" A ", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1) or (1uL shl 2), fromStrStrict("A | B | C", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1) or (1uL shl 2), fromStrStrict("A\n|\tB\r\n|   C ", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1) or (1uL shl 2), fromStrStrict("A|B|C", TestFlags.empty()).bits())
        assertEquals(1uL or (1uL shl 1), fromStrStrict("一 | 二", TestUnicode.empty()).bits())
    }

    @Test
    public fun invalid() {
        val err1 = assertFailsWith<IllegalArgumentException> { fromStr("a", TestFlags.empty()) }
        assertTrue(err1.message?.startsWith("unrecognized named flag") == true)

        val err2 = assertFailsWith<IllegalArgumentException> { fromStr("A & B", TestFlags.empty()) }
        assertTrue(err2.message?.startsWith("unrecognized named flag") == true)

        val err3 = assertFailsWith<IllegalArgumentException> { fromStr("0xg", TestFlags.empty()) }
        assertTrue(err3.message?.startsWith("invalid hex flag") == true)

        val err4 = assertFailsWith<IllegalArgumentException> { fromStrStrict("a", TestFlags.empty()) }
        assertTrue(err4.message?.startsWith("unrecognized named flag") == true)

        val err5 = assertFailsWith<IllegalArgumentException> { fromStrStrict("0x1", TestFlags.empty()) }
        assertTrue(err5.message?.startsWith("invalid hex flag") == true)
    }

    @Test
    public fun cases() {
        // toWriter
        assertEquals("", toText(TestFlags.empty()))
        assertEquals("A", toText(TestFlags.A))
        assertEquals("A | B | C", toText(TestFlags.all()))
        assertEquals("0x8", toText(TestFlags.fromBitsRetain(1uL shl 3)))
        assertEquals("A | 0x8", toText(TestFlags.A or TestFlags.fromBitsRetain(1uL shl 3)))
        assertEquals("", toText(TestZero.ZERO))
        assertEquals("ABC", toText(TestFlagsInvert.all()))
        assertEquals("0x1", toText(TestOverlapping.fromBitsRetain(1uL)))
        assertEquals("A", toText(TestOverlappingFull.C))
        assertEquals("A | D", toText(TestOverlappingFull.C or TestOverlappingFull.D))

        // toWriterTruncate
        assertEquals("", toTextTruncate(TestFlags.empty()))
        assertEquals("A", toTextTruncate(TestFlags.A))
        assertEquals("A | B | C", toTextTruncate(TestFlags.all()))
        assertEquals("", toTextTruncate(TestFlags.fromBitsRetain(1uL shl 3)))
        assertEquals("A", toTextTruncate(TestFlags.A or TestFlags.fromBitsRetain(1uL shl 3)))
        assertEquals("", toTextTruncate(TestZero.ZERO))
        assertEquals("ABC", toTextTruncate(TestFlagsInvert.all()))
        assertEquals("0x1", toTextTruncate(TestOverlapping.fromBitsRetain(1uL)))
        assertEquals("A", toTextTruncate(TestOverlappingFull.C))
        assertEquals("A | D", toTextTruncate(TestOverlappingFull.C or TestOverlappingFull.D))

        // toWriterStrict
        assertEquals("", toTextStrict(TestFlags.empty()))
        assertEquals("A", toTextStrict(TestFlags.A))
        assertEquals("A | B | C", toTextStrict(TestFlags.all()))
        assertEquals("", toTextStrict(TestFlags.fromBitsRetain(1uL shl 3)))
        assertEquals("A", toTextStrict(TestFlags.A or TestFlags.fromBitsRetain(1uL shl 3)))
        assertEquals("", toTextStrict(TestZero.ZERO))
        assertEquals("ABC", toTextStrict(TestFlagsInvert.all()))
        assertEquals("", toTextStrict(TestOverlapping.fromBitsRetain(1uL)))
        assertEquals("A", toTextStrict(TestOverlappingFull.C))
        assertEquals("A | D", toTextStrict(TestOverlappingFull.C or TestOverlappingFull.D))
    }
}
