// port-lint: source example_generated.rs
@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.bitflags

import kotlin.native.HiddenFromObjC

/**
 * Example code shaped like generated flag output.
 */
@HiddenFromObjC
public class ExampleFlags private constructor(
    bits: ULong,
) : BitFlags<ExampleFlags>(bits) {
    override fun flags(): List<Flag<ExampleFlags>> = FLAGS

    override fun fromBitsRetain(bits: ULong): ExampleFlags = ExampleFlags(bits)

    override fun toString(): String = toText(this)

    override fun equals(other: Any?): Boolean =
        other is ExampleFlags && bits() == other.bits()

    override fun hashCode(): Int = bits().hashCode()

    public companion object {
        public val A: ExampleFlags = ExampleFlags(0b00000001u)
        public val B: ExampleFlags = ExampleFlags(0b00000010u)
        public val C: ExampleFlags = ExampleFlags(0b00000100u)
        public val ABC: ExampleFlags = ExampleFlags(A.bits() or B.bits() or C.bits())
        public val UNNAMED_ALL: ExampleFlags = ExampleFlags(ULong.MAX_VALUE)

        public val FLAGS: List<Flag<ExampleFlags>> = listOf(
            Flag("A", A),
            Flag("B", B),
            Flag("C", C),
            Flag("ABC", ABC),
        )

        public val EXTERNAL_FLAGS: List<Flag<ExampleFlags>> = FLAGS + Flag("", UNNAMED_ALL)

        public fun empty(): ExampleFlags = ExampleFlags(0uL)

        public fun all(): ExampleFlags = empty().all()

        public fun fromBits(bits: ULong): ExampleFlags? = empty().fromBits(bits)

        public fun fromBitsTruncate(bits: ULong): ExampleFlags = empty().fromBitsTruncate(bits)

        public fun fromBitsRetain(bits: ULong): ExampleFlags = ExampleFlags(bits)

        public fun fromName(name: String): ExampleFlags? = empty().fromName(name)
    }
}

/**
 * An example flag set with an unnamed catch-all definition.
 */
@HiddenFromObjC
public class ExampleExternalFlags private constructor(
    bits: ULong,
) : BitFlags<ExampleExternalFlags>(bits) {
    override fun flags(): List<Flag<ExampleExternalFlags>> = FLAGS

    override fun fromBitsRetain(bits: ULong): ExampleExternalFlags = ExampleExternalFlags(bits)

    public companion object {
        public val A: ExampleExternalFlags = ExampleExternalFlags(0b00000001u)
        public val B: ExampleExternalFlags = ExampleExternalFlags(0b00000010u)
        public val C: ExampleExternalFlags = ExampleExternalFlags(0b00000100u)
        public val ABC: ExampleExternalFlags = ExampleExternalFlags(A.bits() or B.bits() or C.bits())
        public val UNNAMED_ALL: ExampleExternalFlags = ExampleExternalFlags(ULong.MAX_VALUE)

        public val FLAGS: List<Flag<ExampleExternalFlags>> = listOf(
            Flag("A", A),
            Flag("B", B),
            Flag("C", C),
            Flag("ABC", ABC),
            Flag("", UNNAMED_ALL),
        )

        public fun empty(): ExampleExternalFlags = ExampleExternalFlags(0uL)
    }
}
