// port-lint: source iter.rs
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
)

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
