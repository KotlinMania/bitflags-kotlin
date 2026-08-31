// port-lint: source bitflags/src/iter.rs
@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.bitflags

import kotlin.native.HiddenFromObjC

/**
 * A named flag yielded by flag iterators.
 */
@HiddenFromObjC
public data class NamedFlag<B : BitFlags<B>>(
    public val name: String,
    public val flag: B,
) {
    public fun name(): String = name

    public fun value(): B = flag
}

/**
 * Item yielded by flag iterators.
 */
public typealias Item<B> = NamedFlag<B>

/**
 * An iterator over all defined named flags.
 */
public typealias IterDefinedNames<B> = DefinedNamedFlagIterator<B>

/**
 * An iterator over contained flag values.
 *
 * Defined named flags are yielded first, with any remaining bits yielded as a
 * final flags value.
 */
@HiddenFromObjC
public class FlagIterator<B : BitFlags<B>> internal constructor(
    flags: BitFlags<B>,
) : Iterator<B> {
    private val inner = NamedFlagIterator(flags)
    private var done = false

    public companion object {
        /**
         * Create an iterator over the flags values in [flags].
         */
        public fun <B : BitFlags<B>> new(flags: BitFlags<B>): FlagIterator<B> = FlagIterator(flags)

        /**
         * Internal constructor used by bitflags generation.
         */
        public fun <B : BitFlags<B>> __private_const_new(
            flags: List<Flag<B>>,
            source: B,
            remaining: B,
        ): FlagIterator<B> = FlagIterator(source)
    }

    override fun hasNext(): Boolean = !done && (inner.hasNext() || !inner.remaining().isEmpty())

    override fun next(): B {
        if (inner.hasNext()) {
            return inner.next().flag
        }

        if (!done) {
            done = true
            val remaining = inner.remaining()
            if (!remaining.isEmpty()) {
                return remaining.fromBitsRetain(remaining.bits())
            }
        }

        throw NoSuchElementException()
    }
}

/**
 * An iterator over contained, defined, named flags.
 */
@HiddenFromObjC
public class NamedFlagIterator<B : BitFlags<B>> internal constructor(
    flags: BitFlags<B>,
) : Iterator<NamedFlag<B>> {
    private val definitions = flags.flags()
    private var index = 0
    private var remainingValue = flags.fromBitsRetain(flags.bits())
    private val source = flags.fromBitsRetain(flags.bits())
    private var nextValue: NamedFlag<B>? = null

    /**
     * Bits not yet covered by yielded named flags.
     */
    public fun remaining(): B = remainingValue

    override fun hasNext(): Boolean {
        if (nextValue != null) {
            return true
        }

        while (index < definitions.size) {
            if (remainingValue.isEmpty()) {
                return false
            }

            val flag = definitions[index]
            index += 1

            if (flag.isUnnamed()) {
                continue
            }

            val value = source.fromBitsRetain(flag.value().bits())
            if (source.contains(value) && remainingValue.intersects(value)) {
                remainingValue = remainingValue.remove(value)
                nextValue = NamedFlag(flag.name(), value)
                return true
            }
        }

        return false
    }

    override fun next(): NamedFlag<B> {
        if (!hasNext()) {
            throw NoSuchElementException()
        }

        val value = nextValue ?: throw NoSuchElementException()
        nextValue = null
        return value
    }

    public companion object {
        /**
         * Create an iterator over the named flags in [flags].
         */
        public fun <B : BitFlags<B>> new(flags: BitFlags<B>): NamedFlagIterator<B> = NamedFlagIterator(flags)

        /**
         * Internal constructor used by bitflags generation.
         */
        public fun <B : BitFlags<B>> __private_const_new(
            flags: List<Flag<B>>,
            source: B,
            remaining: B,
        ): NamedFlagIterator<B> = NamedFlagIterator(source)
    }
}

/**
 * An iterator over all defined named flags.
 */
@HiddenFromObjC
public class DefinedNamedFlagIterator<B : BitFlags<B>> internal constructor(
    flags: BitFlags<B>,
) : Iterator<NamedFlag<B>> {
    private val definitions = flags.flags()
    private var index = 0
    private var nextValue: NamedFlag<B>? = null

    public companion object {
        /**
         * Create an iterator over all defined named flags in [flags].
         */
        public fun <B : BitFlags<B>> new(flags: BitFlags<B>): DefinedNamedFlagIterator<B> = DefinedNamedFlagIterator(flags)
    }

    override fun hasNext(): Boolean {
        if (nextValue != null) {
            return true
        }

        while (index < definitions.size) {
            val flag = definitions[index]
            index += 1
            if (flag.isNamed()) {
                nextValue = NamedFlag(flag.name(), flag.value())
                return true
            }
        }

        return false
    }

    override fun next(): NamedFlag<B> {
        if (!hasNext()) {
            throw NoSuchElementException()
        }

        val value = nextValue ?: throw NoSuchElementException()
        nextValue = null
        return value
    }
}
