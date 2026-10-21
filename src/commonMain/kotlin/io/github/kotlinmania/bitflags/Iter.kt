// port-lint: source iter.rs
package io.github.kotlinmania.bitflags

/**
 * A named flag yielded by flag iterators.
 */
public data class NamedFlag<B : Flags<B>>(
    public val name: String,
    public val flag: B,
) {
    public fun name(): String = name

    public fun value(): B = flag
}

/**
 * An iterator over contained flag values.
 *
 * Defined named flags are yielded first, with any remaining bits yielded as a
 * final flags value.
 */
public class Iter<B : Flags<B>> private constructor(
    private val inner: IterNames<B>,
) : Iterator<B> {
    internal constructor(flags: Flags<B>) : this(IterNames(flags))

    private var done = false

    public companion object {
        /**
         * Create an iterator over the flags values in [flags].
         */
        public fun <B : Flags<B>> new(flags: Flags<B>): Iter<B> = Iter(flags)

        /**
         * Internal constructor used by bitflags generation.
         */
        public fun <B : Flags<B>> privateConstNew(
            flags: List<Flag<B>>,
            source: B,
            remaining: B,
        ): Iter<B> = Iter(IterNames.privateConstNew(flags, source, remaining))
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
public class IterNames<B : Flags<B>> private constructor(
    private val definitions: List<Flag<B>>,
    private val source: B,
    private var remainingValue: B,
) : Iterator<NamedFlag<B>> {
    internal constructor(flags: Flags<B>) : this(
        flags.flags(),
        flags.fromBitsRetain(flags.bits()),
        flags.fromBitsRetain(flags.bits()),
    )

    private var index = 0
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
        public fun <B : Flags<B>> new(flags: Flags<B>): IterNames<B> = IterNames(flags)

        /**
         * Internal constructor used by bitflags generation.
         */
        public fun <B : Flags<B>> privateConstNew(
            flags: List<Flag<B>>,
            source: B,
            remaining: B,
        ): IterNames<B> = IterNames(flags, source, remaining)
    }
}

/**
 * An iterator over all defined named flags.
 */
public class IterDefinedNames<B : Flags<B>> internal constructor(
    flags: Flags<B>,
) : Iterator<NamedFlag<B>> {
    private val definitions = flags.flags()
    private var index = 0
    private var nextValue: NamedFlag<B>? = null

    public companion object {
        /**
         * Create an iterator over all defined named flags in [flags].
         */
        public fun <B : Flags<B>> new(flags: Flags<B>): IterDefinedNames<B> = IterDefinedNames(flags)
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
