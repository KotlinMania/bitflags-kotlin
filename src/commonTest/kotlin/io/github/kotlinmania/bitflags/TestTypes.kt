// port-lint: source tests.rs
@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.bitflags

import kotlin.native.HiddenFromObjC

@HiddenFromObjC
public class TestFlags private constructor(
    bits: ULong,
) : BitFlags<TestFlags>(bits) {
    override fun flags(): List<Flag<TestFlags>> = FLAGS
    override fun fromBitsRetain(bits: ULong): TestFlags = TestFlags(bits)
    override fun toString(): String = "TestFlags(${toDebugText(this)})"
    override fun equals(other: Any?): Boolean = other is TestFlags && bits() == other.bits()
    override fun hashCode(): Int = bits().hashCode()

    public companion object {
        public val A: TestFlags = TestFlags(1uL)
        public val B: TestFlags = TestFlags(1uL shl 1)
        public val C: TestFlags = TestFlags(1uL shl 2)
        public val ABC: TestFlags = TestFlags(1uL or (1uL shl 1) or (1uL shl 2))

        public val FLAGS: List<Flag<TestFlags>> = listOf(
            Flag("A", A),
            Flag("B", B),
            Flag("C", C),
            Flag("ABC", ABC),
        )

        public fun empty(): TestFlags = TestFlags(0uL)
        public fun all(): TestFlags = empty().all()
        public fun fromBits(bits: ULong): TestFlags? = empty().fromBits(bits)
        public fun fromBits(bits: Number): TestFlags? = empty().fromBits(bits.toLong().toULong())
        public fun fromBitsTruncate(bits: ULong): TestFlags = empty().fromBitsTruncate(bits)
        public fun fromBitsTruncate(bits: Number): TestFlags = empty().fromBitsTruncate(bits.toLong().toULong())
        public fun fromBitsRetain(bits: ULong): TestFlags = TestFlags(bits)
        public fun fromBitsRetain(bits: Number): TestFlags = TestFlags(bits.toLong().toULong())
        public fun fromName(name: String): TestFlags? = empty().fromName(name)
        public fun iter(): FlagIterator<TestFlags> = empty().iter()
        public fun iterNames(): NamedFlagIterator<TestFlags> = empty().iterNames()
    }
}

@HiddenFromObjC
public class TestFlagsInvert private constructor(
    bits: ULong,
) : BitFlags<TestFlagsInvert>(bits) {
    override fun flags(): List<Flag<TestFlagsInvert>> = FLAGS
    override fun fromBitsRetain(bits: ULong): TestFlagsInvert = TestFlagsInvert(bits)
    override fun toString(): String = "TestFlagsInvert(${toDebugText(this)})"
    override fun equals(other: Any?): Boolean = other is TestFlagsInvert && bits() == other.bits()
    override fun hashCode(): Int = bits().hashCode()

    public companion object {
        public val A: TestFlagsInvert = TestFlagsInvert(1uL)
        public val B: TestFlagsInvert = TestFlagsInvert(1uL shl 1)
        public val C: TestFlagsInvert = TestFlagsInvert(1uL shl 2)
        public val ABC: TestFlagsInvert = TestFlagsInvert(1uL or (1uL shl 1) or (1uL shl 2))

        public val FLAGS: List<Flag<TestFlagsInvert>> = listOf(
            Flag("ABC", ABC),
            Flag("A", A),
            Flag("B", B),
            Flag("C", C),
        )

        public fun empty(): TestFlagsInvert = TestFlagsInvert(0uL)
        public fun all(): TestFlagsInvert = empty().all()
        public fun fromBits(bits: ULong): TestFlagsInvert? = empty().fromBits(bits)
        public fun fromBits(bits: Number): TestFlagsInvert? = empty().fromBits(bits.toLong().toULong())
        public fun fromBitsTruncate(bits: ULong): TestFlagsInvert = empty().fromBitsTruncate(bits)
        public fun fromBitsTruncate(bits: Number): TestFlagsInvert = empty().fromBitsTruncate(bits.toLong().toULong())
        public fun fromBitsRetain(bits: ULong): TestFlagsInvert = TestFlagsInvert(bits)
        public fun fromBitsRetain(bits: Number): TestFlagsInvert = TestFlagsInvert(bits.toLong().toULong())
        public fun fromName(name: String): TestFlagsInvert? = empty().fromName(name)
        public fun iter(): FlagIterator<TestFlagsInvert> = empty().iter()
        public fun iterNames(): NamedFlagIterator<TestFlagsInvert> = empty().iterNames()
    }
}

@HiddenFromObjC
public class TestZero private constructor(
    bits: ULong,
) : BitFlags<TestZero>(bits) {
    override fun flags(): List<Flag<TestZero>> = FLAGS
    override fun fromBitsRetain(bits: ULong): TestZero = TestZero(bits)
    override fun toString(): String = "TestZero(${toDebugText(this)})"
    override fun equals(other: Any?): Boolean = other is TestZero && bits() == other.bits()
    override fun hashCode(): Int = bits().hashCode()

    public companion object {
        public val ZERO: TestZero = TestZero(0uL)

        public val FLAGS: List<Flag<TestZero>> = listOf(
            Flag("ZERO", ZERO),
        )

        public fun empty(): TestZero = TestZero(0uL)
        public fun all(): TestZero = empty().all()
        public fun fromBits(bits: ULong): TestZero? = empty().fromBits(bits)
        public fun fromBits(bits: Number): TestZero? = empty().fromBits(bits.toLong().toULong())
        public fun fromBitsTruncate(bits: ULong): TestZero = empty().fromBitsTruncate(bits)
        public fun fromBitsTruncate(bits: Number): TestZero = empty().fromBitsTruncate(bits.toLong().toULong())
        public fun fromBitsRetain(bits: ULong): TestZero = TestZero(bits)
        public fun fromBitsRetain(bits: Number): TestZero = TestZero(bits.toLong().toULong())
        public fun fromName(name: String): TestZero? = empty().fromName(name)
        public fun iter(): FlagIterator<TestZero> = empty().iter()
        public fun iterNames(): NamedFlagIterator<TestZero> = empty().iterNames()
    }
}

@HiddenFromObjC
public class TestZeroOne private constructor(
    bits: ULong,
) : BitFlags<TestZeroOne>(bits) {
    override fun flags(): List<Flag<TestZeroOne>> = FLAGS
    override fun fromBitsRetain(bits: ULong): TestZeroOne = TestZeroOne(bits)
    override fun toString(): String = "TestZeroOne(${toDebugText(this)})"
    override fun equals(other: Any?): Boolean = other is TestZeroOne && bits() == other.bits()
    override fun hashCode(): Int = bits().hashCode()

    public companion object {
        public val ZERO: TestZeroOne = TestZeroOne(0uL)
        public val ONE: TestZeroOne = TestZeroOne(1uL)

        public val FLAGS: List<Flag<TestZeroOne>> = listOf(
            Flag("ZERO", ZERO),
            Flag("ONE", ONE),
        )

        public fun empty(): TestZeroOne = TestZeroOne(0uL)
        public fun all(): TestZeroOne = empty().all()
        public fun fromBits(bits: ULong): TestZeroOne? = empty().fromBits(bits)
        public fun fromBits(bits: Number): TestZeroOne? = empty().fromBits(bits.toLong().toULong())
        public fun fromBitsTruncate(bits: ULong): TestZeroOne = empty().fromBitsTruncate(bits)
        public fun fromBitsTruncate(bits: Number): TestZeroOne = empty().fromBitsTruncate(bits.toLong().toULong())
        public fun fromBitsRetain(bits: ULong): TestZeroOne = TestZeroOne(bits)
        public fun fromBitsRetain(bits: Number): TestZeroOne = TestZeroOne(bits.toLong().toULong())
        public fun fromName(name: String): TestZeroOne? = empty().fromName(name)
    }
}

@HiddenFromObjC
public class TestUnicode private constructor(
    bits: ULong,
) : BitFlags<TestUnicode>(bits) {
    override fun flags(): List<Flag<TestUnicode>> = FLAGS
    override fun fromBitsRetain(bits: ULong): TestUnicode = TestUnicode(bits)
    override fun toString(): String = "TestUnicode(${toDebugText(this)})"
    override fun equals(other: Any?): Boolean = other is TestUnicode && bits() == other.bits()
    override fun hashCode(): Int = bits().hashCode()

    public companion object {
        public val 一: TestUnicode = TestUnicode(1uL)
        public val 二: TestUnicode = TestUnicode(1uL shl 1)

        public val FLAGS: List<Flag<TestUnicode>> = listOf(
            Flag("一", 一),
            Flag("二", 二),
        )

        public fun empty(): TestUnicode = TestUnicode(0uL)
        public fun all(): TestUnicode = empty().all()
        public fun fromBits(bits: ULong): TestUnicode? = empty().fromBits(bits)
        public fun fromBits(bits: Number): TestUnicode? = empty().fromBits(bits.toLong().toULong())
        public fun fromBitsTruncate(bits: ULong): TestUnicode = empty().fromBitsTruncate(bits)
        public fun fromBitsTruncate(bits: Number): TestUnicode = empty().fromBitsTruncate(bits.toLong().toULong())
        public fun fromBitsRetain(bits: ULong): TestUnicode = TestUnicode(bits)
        public fun fromBitsRetain(bits: Number): TestUnicode = TestUnicode(bits.toLong().toULong())
        public fun fromName(name: String): TestUnicode? = empty().fromName(name)
    }
}

@HiddenFromObjC
public class TestEmpty private constructor(
    bits: ULong,
) : BitFlags<TestEmpty>(bits) {
    override fun flags(): List<Flag<TestEmpty>> = FLAGS
    override fun fromBitsRetain(bits: ULong): TestEmpty = TestEmpty(bits)
    override fun toString(): String = "TestEmpty(${toDebugText(this)})"
    override fun equals(other: Any?): Boolean = other is TestEmpty && bits() == other.bits()
    override fun hashCode(): Int = bits().hashCode()

    public companion object {
        public val FLAGS: List<Flag<TestEmpty>> = emptyList()

        public fun empty(): TestEmpty = TestEmpty(0uL)
        public fun all(): TestEmpty = empty().all()
        public fun fromBits(bits: ULong): TestEmpty? = empty().fromBits(bits)
        public fun fromBits(bits: Number): TestEmpty? = empty().fromBits(bits.toLong().toULong())
        public fun fromBitsTruncate(bits: ULong): TestEmpty = empty().fromBitsTruncate(bits)
        public fun fromBitsTruncate(bits: Number): TestEmpty = empty().fromBitsTruncate(bits.toLong().toULong())
        public fun fromBitsRetain(bits: ULong): TestEmpty = TestEmpty(bits)
        public fun fromBitsRetain(bits: Number): TestEmpty = TestEmpty(bits.toLong().toULong())
        public fun fromName(name: String): TestEmpty? = empty().fromName(name)
    }
}

@HiddenFromObjC
public class TestOverlapping private constructor(
    bits: ULong,
) : BitFlags<TestOverlapping>(bits) {
    override fun flags(): List<Flag<TestOverlapping>> = FLAGS
    override fun fromBitsRetain(bits: ULong): TestOverlapping = TestOverlapping(bits)
    override fun toString(): String = "TestOverlapping(${toDebugText(this)})"
    override fun equals(other: Any?): Boolean = other is TestOverlapping && bits() == other.bits()
    override fun hashCode(): Int = bits().hashCode()

    public companion object {
        public val AB: TestOverlapping = TestOverlapping(1uL or (1uL shl 1))
        public val BC: TestOverlapping = TestOverlapping((1uL shl 1) or (1uL shl 2))

        public val FLAGS: List<Flag<TestOverlapping>> = listOf(
            Flag("AB", AB),
            Flag("BC", BC),
        )

        public fun empty(): TestOverlapping = TestOverlapping(0uL)
        public fun all(): TestOverlapping = empty().all()
        public fun fromBits(bits: ULong): TestOverlapping? = empty().fromBits(bits)
        public fun fromBits(bits: Number): TestOverlapping? = empty().fromBits(bits.toLong().toULong())
        public fun fromBitsTruncate(bits: ULong): TestOverlapping = empty().fromBitsTruncate(bits)
        public fun fromBitsTruncate(bits: Number): TestOverlapping = empty().fromBitsTruncate(bits.toLong().toULong())
        public fun fromBitsRetain(bits: ULong): TestOverlapping = TestOverlapping(bits)
        public fun fromBitsRetain(bits: Number): TestOverlapping = TestOverlapping(bits.toLong().toULong())
        public fun fromName(name: String): TestOverlapping? = empty().fromName(name)
    }
}

@HiddenFromObjC
public class TestOverlappingFull private constructor(
    bits: ULong,
) : BitFlags<TestOverlappingFull>(bits) {
    override fun flags(): List<Flag<TestOverlappingFull>> = FLAGS
    override fun fromBitsRetain(bits: ULong): TestOverlappingFull = TestOverlappingFull(bits)
    override fun toString(): String = "TestOverlappingFull(${toDebugText(this)})"
    override fun equals(other: Any?): Boolean = other is TestOverlappingFull && bits() == other.bits()
    override fun hashCode(): Int = bits().hashCode()

    public companion object {
        public val A: TestOverlappingFull = TestOverlappingFull(1uL)
        public val B: TestOverlappingFull = TestOverlappingFull(1uL)
        public val C: TestOverlappingFull = TestOverlappingFull(1uL)
        public val D: TestOverlappingFull = TestOverlappingFull(1uL shl 1)

        public val FLAGS: List<Flag<TestOverlappingFull>> = listOf(
            Flag("A", A),
            Flag("B", B),
            Flag("C", C),
            Flag("D", D),
        )

        public fun empty(): TestOverlappingFull = TestOverlappingFull(0uL)
        public fun all(): TestOverlappingFull = empty().all()
        public fun fromBits(bits: ULong): TestOverlappingFull? = empty().fromBits(bits)
        public fun fromBits(bits: Number): TestOverlappingFull? = empty().fromBits(bits.toLong().toULong())
        public fun fromBitsTruncate(bits: ULong): TestOverlappingFull = empty().fromBitsTruncate(bits)
        public fun fromBitsTruncate(bits: Number): TestOverlappingFull = empty().fromBitsTruncate(bits.toLong().toULong())
        public fun fromBitsRetain(bits: ULong): TestOverlappingFull = TestOverlappingFull(bits)
        public fun fromBitsRetain(bits: Number): TestOverlappingFull = TestOverlappingFull(bits.toLong().toULong())
        public fun fromName(name: String): TestOverlappingFull? = empty().fromName(name)
        public fun iter(): FlagIterator<TestOverlappingFull> = empty().iter()
        public fun iterNames(): NamedFlagIterator<TestOverlappingFull> = empty().iterNames()
    }
}

@HiddenFromObjC
public class TestExternal private constructor(
    bits: ULong,
) : BitFlags<TestExternal>(bits) {
    override fun flags(): List<Flag<TestExternal>> = FLAGS
    override fun fromBitsRetain(bits: ULong): TestExternal = TestExternal(bits)
    override fun toString(): String = "TestExternal(${toDebugText(this)})"
    override fun equals(other: Any?): Boolean = other is TestExternal && bits() == other.bits()
    override fun hashCode(): Int = bits().hashCode()

    public companion object {
        public val A: TestExternal = TestExternal(1uL)
        public val B: TestExternal = TestExternal(1uL shl 1)
        public val C: TestExternal = TestExternal(1uL shl 2)
        public val ABC: TestExternal = TestExternal(1uL or (1uL shl 1) or (1uL shl 2))
        public val UNNAMED_ALL: TestExternal = TestExternal(0xFFuL)

        public val FLAGS: List<Flag<TestExternal>> = listOf(
            Flag("A", A),
            Flag("B", B),
            Flag("C", C),
            Flag("ABC", ABC),
            Flag("", UNNAMED_ALL),
        )

        public fun empty(): TestExternal = TestExternal(0uL)
        public fun all(): TestExternal = TestExternal(0xFFuL)
        public fun fromBits(bits: ULong): TestExternal? = empty().fromBits(bits)
        public fun fromBits(bits: Number): TestExternal? = empty().fromBits(bits.toLong().toULong())
        public fun fromBitsTruncate(bits: ULong): TestExternal = empty().fromBitsTruncate(bits)
        public fun fromBitsTruncate(bits: Number): TestExternal = empty().fromBitsTruncate(bits.toLong().toULong())
        public fun fromBitsRetain(bits: ULong): TestExternal = TestExternal(bits)
        public fun fromBitsRetain(bits: Number): TestExternal = TestExternal(bits.toLong().toULong())
        public fun fromName(name: String): TestExternal? = empty().fromName(name)
        public fun iter(): FlagIterator<TestExternal> = empty().iter()
        public fun iterNames(): NamedFlagIterator<TestExternal> = empty().iterNames()
    }
}

@HiddenFromObjC
public class TestExternalFull private constructor(
    bits: ULong,
) : BitFlags<TestExternalFull>(bits) {
    override fun flags(): List<Flag<TestExternalFull>> = FLAGS
    override fun fromBitsRetain(bits: ULong): TestExternalFull = TestExternalFull(bits)
    override fun toString(): String = "TestExternalFull(${toDebugText(this)})"
    override fun equals(other: Any?): Boolean = other is TestExternalFull && bits() == other.bits()
    override fun hashCode(): Int = bits().hashCode()

    public companion object {
        public val UNNAMED_ALL: TestExternalFull = TestExternalFull(0xFFuL)

        public val FLAGS: List<Flag<TestExternalFull>> = listOf(
            Flag("", UNNAMED_ALL),
        )

        public fun empty(): TestExternalFull = TestExternalFull(0uL)
        public fun all(): TestExternalFull = TestExternalFull(0xFFuL)
        public fun fromBits(bits: ULong): TestExternalFull? = empty().fromBits(bits)
        public fun fromBits(bits: Number): TestExternalFull? = empty().fromBits(bits.toLong().toULong())
        public fun fromBitsTruncate(bits: ULong): TestExternalFull = empty().fromBitsTruncate(bits)
        public fun fromBitsTruncate(bits: Number): TestExternalFull = empty().fromBitsTruncate(bits.toLong().toULong())
        public fun fromBitsRetain(bits: ULong): TestExternalFull = TestExternalFull(bits)
        public fun fromBitsRetain(bits: Number): TestExternalFull = TestExternalFull(bits.toLong().toULong())
        public fun fromName(name: String): TestExternalFull? = empty().fromName(name)
    }
}

private fun <B : BitFlags<B>> toDebugText(flags: B): String {
    val text = toText(flags)
    return if (text.isEmpty()) "0x0" else text
}
