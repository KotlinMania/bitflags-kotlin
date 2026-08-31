// port-lint: tests bitflags/src/src/tests/bitflags_match.rs
@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.bitflags

import kotlin.native.HiddenFromObjC
import kotlin.test.Test
import kotlin.test.assertEquals

@HiddenFromObjC
public class MatchFlags private constructor(
    bits: ULong,
) : BitFlags<MatchFlags>(bits) {
    override fun flags(): List<Flag<MatchFlags>> = FLAGS

    override fun fromBitsRetain(bits: ULong): MatchFlags = MatchFlags(bits)

    override fun equals(other: Any?): Boolean = other is MatchFlags && bits() == other.bits()

    override fun hashCode(): Int = bits().hashCode()

    public companion object {
        public val A: MatchFlags = MatchFlags(1uL shl 0)
        public val B: MatchFlags = MatchFlags(1uL shl 1)
        public val C: MatchFlags = MatchFlags(1uL shl 2)
        public val D: MatchFlags = MatchFlags(1uL shl 3)

        public val FLAGS: List<Flag<MatchFlags>> =
            listOf(
                Flag("A", A),
                Flag("B", B),
                Flag("C", C),
                Flag("D", D),
            )

        public fun empty(): MatchFlags = MatchFlags(0uL)

        public fun all(): MatchFlags = empty().all()
    }
}

public class BitflagsMatchTest {
    private fun flagToString(flag: MatchFlags): String =
        when {
            flag == MatchFlags.A -> "A"
            flag == MatchFlags.B -> "B"
            flag == MatchFlags.C -> "C"
            flag == MatchFlags.D -> "D"
            flag == (MatchFlags.A or MatchFlags.B) -> "A or B"
            flag == (MatchFlags.A and MatchFlags.B) -> "A and B | empty"
            flag == (MatchFlags.A xor MatchFlags.B) -> "A xor B"
            flag == (MatchFlags.A or MatchFlags.B or MatchFlags.C) -> "A or B or C"
            flag == (MatchFlags.A and MatchFlags.B and MatchFlags.C) -> "A and B and C"
            flag == (MatchFlags.A xor MatchFlags.B xor MatchFlags.C) -> "A xor B xor C"
            flag == (MatchFlags.A or MatchFlags.B or MatchFlags.C or MatchFlags.D) -> "All flags"
            else -> "Unknown combination"
        }

    @Test
    public fun testSingleFlags() {
        assertEquals("A", flagToString(MatchFlags.A))
        assertEquals("B", flagToString(MatchFlags.B))
        assertEquals("C", flagToString(MatchFlags.C))
        assertEquals("D", flagToString(MatchFlags.D))
    }

    @Test
    public fun testOrOperations() {
        assertEquals("A or B", flagToString(MatchFlags.A or MatchFlags.B))
        assertEquals("A or B or C", flagToString(MatchFlags.A or MatchFlags.B or MatchFlags.C))
        assertEquals("All flags", flagToString(MatchFlags.A or MatchFlags.B or MatchFlags.C or MatchFlags.D))
    }

    @Test
    public fun testAndOperations() {
        assertEquals("A", flagToString(MatchFlags.A and MatchFlags.A))
        assertEquals("A and B | empty", flagToString(MatchFlags.A and MatchFlags.B))
        assertEquals("A and B | empty", flagToString(MatchFlags.A and MatchFlags.B and MatchFlags.C))
        assertEquals("A and B | empty", flagToString(MatchFlags.A and MatchFlags.B and MatchFlags.C and MatchFlags.D))
    }

    @Test
    public fun testXorOperations() {
        assertEquals("A or B", flagToString(MatchFlags.A xor MatchFlags.B))
        assertEquals("A and B | empty", flagToString(MatchFlags.A xor MatchFlags.A))
        assertEquals("A or B or C", flagToString(MatchFlags.A xor MatchFlags.B xor MatchFlags.C))
    }

    @Test
    public fun testComplexOperations() {
        assertEquals("A", flagToString(MatchFlags.A or (MatchFlags.B and MatchFlags.C)))
        assertEquals("B", flagToString((MatchFlags.A or MatchFlags.B) and (MatchFlags.B or MatchFlags.C)))
        assertEquals("A or B or C", flagToString(MatchFlags.A xor (MatchFlags.B or MatchFlags.C)))
    }

    @Test
    public fun testEmptyAndFullFlags() {
        assertEquals("A and B | empty", flagToString(MatchFlags.empty()))
        assertEquals("All flags", flagToString(MatchFlags.all()))
    }
}
