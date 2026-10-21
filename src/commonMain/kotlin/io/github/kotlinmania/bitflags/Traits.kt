// port-lint: source traits.rs
package io.github.kotlinmania.bitflags

/**
 * A defined flag value that may be named or unnamed.
 */
public data class Flag<B>(
    private val flagName: String,
    private val flagValue: B,
) {
    /**
     * Get the name of this flag. Unnamed flags return an empty string.
     */
    public fun name(): String = flagName

    /**
     * Get this flag's value.
     */
    public fun value(): B = flagValue

    /**
     * Whether this flag has a non-empty name.
     */
    public fun isNamed(): Boolean = flagName.isNotEmpty()

    /**
     * Whether this flag has no name.
     */
    public fun isUnnamed(): Boolean = flagName.isEmpty()

    public companion object {
        /**
         * Create a new flag with the given name and value.
         */
        public fun <B> new(name: String, value: B): Flag<B> = Flag(name, value)
    }
}

/**
 * Underlying integer storage representation for flags.
 */
public interface Bits

/**
 * Marker for primitive bit integer types.
 */
public interface Primitive

/**
 * Marker for public flags structures.
 */
public interface PublicFlags

/**
 * Marker interface for types generated or implementing the bitflags macro pattern.
 */
public interface ImplementedByBitFlagsMacro

/**
 * A set of defined flags using an unsigned integer as storage.
 *
 * Concrete flag sets define their named values and exact bits conversion.
 */
public abstract class Flags<B : Flags<B>>(
    private val rawBits: ULong,
) {
    /**
     * The flags known by this flag set.
     */
    public abstract fun flags(): List<Flag<B>>

    /**
     * Convert from a bits value exactly.
     */
    public abstract fun fromBitsRetain(bits: ULong): B

    /**
     * Get a flags value with all bits unset.
     */
    public fun empty(): B = fromBitsRetain(0uL)

    /**
     * Get a flags value with all known bits set.
     */
    public fun all(): B {
        var truncated = 0uL
        for (flag in flags()) {
            truncated = truncated or flag.value().bits()
        }
        return fromBitsRetain(truncated)
    }

    /**
     * Get the known bits from this flags value.
     */
    public fun knownBits(): ULong = bits() and all().bits()

    /**
     * Get the unknown bits from this flags value.
     */
    public fun unknownBits(): ULong = bits() and all().bits().inv()

    /**
     * Whether any unknown bits are set.
     */
    public fun containsUnknownBits(): Boolean = unknownBits() != 0uL

    /**
     * Get the underlying bits value.
     */
    public fun bits(): ULong = rawBits

    /**
     * Convert from a bits value, returning null if unknown bits are set.
     */
    public fun fromBits(bits: ULong): B? {
        val truncated = fromBitsTruncate(bits)
        return if (truncated.bits() == bits) truncated else null
    }

    /**
     * Convert from a bits value, unsetting any unknown bits.
     */
    public fun fromBitsTruncate(bits: ULong): B = fromBitsRetain(bits and all().bits())

    /**
     * Get a flags value with the bits of a named flag set.
     */
    public fun fromName(name: String): B? {
        if (name.isEmpty()) {
            return null
        }

        for (flag in flags()) {
            if (flag.name() == name) {
                return fromBitsRetain(flag.value().bits())
            }
        }

        return null
    }

    /**
     * Yield contained named flag values, followed by any remaining unknown bits.
     */
    public fun iter(): Iter<B> = Iter(this)

    /**
     * Yield contained named flag values only.
     */
    public fun iterNames(): IterNames<B> = IterNames(this)

    /**
     * Yield all named flags defined by this flag set.
     */
    public fun iterDefinedNames(): IterDefinedNames<B> = IterDefinedNames(this)

    /**
     * Whether all bits are unset.
     */
    public fun isEmpty(): Boolean = bits() == 0uL

    /**
     * Whether all known bits are set.
     */
    public fun isAll(): Boolean = (all().bits() or bits()) == bits()

    /**
     * Whether any set bits in [other] are also set in this value.
     */
    public fun intersects(other: B): Boolean = (bits() and other.bits()) != 0uL

    /**
     * Whether all set bits in [other] are also set in this value.
     */
    public fun contains(other: B): Boolean = (bits() and other.bits()) == other.bits()

    /**
     * Remove unknown bits.
     */
    public fun truncate(): B = fromBitsTruncate(bits())

    /**
     * The bitwise or of the bits in two flag values.
     */
    public fun insert(other: B): B = union(other)

    /**
     * The intersection of this value with the complement of [other].
     */
    public fun remove(other: B): B = difference(other)

    /**
     * The bitwise exclusive-or of the bits in two flag values.
     */
    public fun toggle(other: B): B = symmetricDifference(other)

    /**
     * Insert [other] when [value] is true, otherwise remove it.
     */
    public fun set(other: B, value: Boolean): B = if (value) insert(other) else remove(other)

    /**
     * Unset all bits.
     */
    public fun clear(): B = empty()

    /**
     * The bitwise and of two flag values.
     */
    public fun intersection(other: B): B = fromBitsRetain(bits() and other.bits())

    /**
     * The bitwise or of two flag values.
     */
    public fun union(other: B): B = fromBitsRetain(bits() or other.bits())

    /**
     * The intersection of this value with the complement of [other].
     */
    public fun difference(other: B): B = fromBitsRetain(bits() and other.bits().inv())

    /**
     * The bitwise exclusive-or of two flag values.
     */
    public fun symmetricDifference(other: B): B = fromBitsRetain(bits() xor other.bits())

    /**
     * The bitwise negation of this flags value, truncated to known bits.
     */
    public fun complement(): B = fromBitsTruncate(bits().inv())

    public infix fun or(other: B): B = union(other)

    public infix fun and(other: B): B = intersection(other)

    public infix fun xor(other: B): B = symmetricDifference(other)

    public operator fun not(): B = complement()

    public operator fun minus(other: B): B = difference(other)

    public operator fun plus(other: B): B = union(other)

    public operator fun compareTo(other: B): Int = bits().compareTo(other.bits())
}

/**
 * Legacy flags contract for macro-generated flag sets.
 */
public abstract class BitFlags<B : BitFlags<B>>(
    bits: ULong,
) : Flags<B>(bits), ImplementedByBitFlagsMacro
