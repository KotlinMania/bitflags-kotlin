// port-lint: source bitflags/src/parser.rs
@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.bitflags

import kotlin.native.HiddenFromObjC

/**
 * Encode a value as a hex string without `0x` prefix.
 */
@HiddenFromObjC
public interface WriteHex {
    /**
     * Write the value as hex into [writer].
     */
    public fun writeHex(writer: Appendable)
}

/**
 * Parse a value from a hex string without `0x` prefix.
 */
@HiddenFromObjC
public interface ParseHex<T> {
    /**
     * Parse the value from hex string [input].
     */
    public fun parseHex(input: String): T
}

/**
 * The kind of parse error encountered while reading flags from text.
 */
public enum class ParseErrorKind {
    EMPTY_FLAG,
    INVALID_NAMED_FLAG,
    INVALID_HEX_FLAG,
}

/**
 * Details for a failed flag parse.
 */
public data class ParseError(
    public val kind: ParseErrorKind,
    public val value: String = "",
) {
    override fun toString(): String =
        when (kind) {
            ParseErrorKind.EMPTY_FLAG -> "encountered empty flag"
            ParseErrorKind.INVALID_NAMED_FLAG -> "unrecognized named flag `$value`"
            ParseErrorKind.INVALID_HEX_FLAG -> "invalid hex flag `$value`"
        }

    public companion object {
        /**
         * An invalid hex flag was encountered.
         */
        public fun invalidHexFlag(flag: Any): ParseError =
            ParseError(ParseErrorKind.INVALID_HEX_FLAG, flag.toString())

        /**
         * A named flag that doesn't correspond to any on the flags type was encountered.
         */
        public fun invalidNamedFlag(flag: Any): ParseError =
            ParseError(ParseErrorKind.INVALID_NAMED_FLAG, flag.toString())

        /**
         * A hex or named flag wasn't found between separators.
         */
        public fun emptyFlag(): ParseError =
            ParseError(ParseErrorKind.EMPTY_FLAG)
    }
}

/**
 * Display adapter for flags.
 */
@HiddenFromObjC
public class AsDisplay<B : BitFlags<B>>(
    public val flags: B,
) {
    /**
     * Format the flags into [writer].
     */
    public fun fmt(writer: Appendable) {
        toWriter(flags, writer)
    }

    override fun toString(): String = toText(flags)
}

/**
 * Write a flags value as text to [writer].
 *
 * Any bits that aren't part of a contained flag will be formatted as a hex number.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> toWriter(flags: B, writer: Appendable) {
    var first = true
    val iter = flags.iterNames()

    while (iter.hasNext()) {
        if (!first) {
            writer.append(" | ")
        }
        first = false
        writer.append(iter.next().name)
    }

    val remaining = iter.remaining().bits()
    if (remaining != 0uL) {
        if (!first) {
            writer.append(" | ")
        }
        writer.append("0x")
        writer.append(remaining.toString(16))
    }
}

/**
 * Write a flags value as text.
 *
 * Bits not part of a contained named flag are formatted as a hexadecimal value.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> toText(flags: B): String =
    buildString { toWriter(flags, this) }

/**
 * Parse a flags value from text.
 *
 * This function will fail on any names that don't correspond to defined flags.
 * Unknown bits will be retained.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> fromStr(input: String, seed: B): B {
    var parsedFlags = seed.empty()
    val trimmed = input.trim()

    if (trimmed.isEmpty()) {
        return parsedFlags
    }

    for (part in input.split('|')) {
        val flag = part.trim()
        if (flag.isEmpty()) {
            throw IllegalArgumentException(ParseError.emptyFlag().toString())
        }

        val parsedFlag =
            if (flag.startsWith("0x")) {
                val hex = flag.removePrefix("0x")
                val bits =
                    hex.toULongOrNull(16)
                        ?: throw IllegalArgumentException(ParseError.invalidHexFlag(hex).toString())
                seed.fromBitsRetain(bits)
            } else {
                seed.fromName(flag)
                    ?: throw IllegalArgumentException(ParseError.invalidNamedFlag(flag).toString())
            }

        parsedFlags = parsedFlags.insert(parsedFlag)
    }

    return parsedFlags
}

/**
 * Parse a flags value from text.
 *
 * Alias for [fromStr].
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> fromText(input: String, seed: B): B = fromStr(input, seed)

/**
 * Write a flags value as text to [writer], ignoring any unknown bits.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> toWriterTruncate(flags: B, writer: Appendable) {
    toWriter(flags.fromBitsTruncate(flags.bits()), writer)
}

/**
 * Write a flags value as text, ignoring unknown bits.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> toTextTruncate(flags: B): String =
    buildString { toWriterTruncate(flags, this) }

/**
 * Parse a flags value from text.
 *
 * This function will fail on any names that don't correspond to defined flags.
 * Unknown bits will be ignored.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> fromStrTruncate(input: String, seed: B): B =
    seed.fromBitsTruncate(fromStr(input, seed).bits())

/**
 * Parse a flags value from text and discard unknown bits.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> fromTextTruncate(input: String, seed: B): B = fromStrTruncate(input, seed)

/**
 * Write only the contained, defined, named flags in a flags value as text into [writer].
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> toWriterStrict(flags: B, writer: Appendable) {
    var first = true
    val iter = flags.iterNames()

    while (iter.hasNext()) {
        if (!first) {
            writer.append(" | ")
        }
        first = false
        writer.append(iter.next().name)
    }
}

/**
 * Write only contained, defined, named flags in a flags value as text.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> toTextStrict(flags: B): String =
    buildString { toWriterStrict(flags, this) }

/**
 * Parse a flags value from text.
 *
 * This function will fail on any names that don't correspond to defined flags.
 * This function will fail to parse hex values.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> fromStrStrict(input: String, seed: B): B {
    var parsedFlags = seed.empty()
    val trimmed = input.trim()

    if (trimmed.isEmpty()) {
        return parsedFlags
    }

    for (part in input.split('|')) {
        val flag = part.trim()
        if (flag.isEmpty()) {
            throw IllegalArgumentException(ParseError.emptyFlag().toString())
        }

        if (flag.startsWith("0x")) {
            throw IllegalArgumentException(
                ParseError.invalidHexFlag("unsupported hex flag value").toString(),
            )
        }

        val parsedFlag =
            seed.fromName(flag)
                ?: throw IllegalArgumentException(ParseError.invalidNamedFlag(flag).toString())
        parsedFlags = parsedFlags.insert(parsedFlag)
    }

    return parsedFlags
}

/**
 * Parse a flags value from names only.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> fromTextStrict(input: String, seed: B): B = fromStrStrict(input, seed)
