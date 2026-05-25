// port-lint: source parser.rs
@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.bitflags

import kotlin.native.HiddenFromObjC

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
)

/**
 * Write a flags value as text.
 *
 * Bits not part of a contained named flag are formatted as a hexadecimal value.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> toText(flags: B): String {
    val parts = mutableListOf<String>()
    val names = flags.iterNames()

    while (names.hasNext()) {
        parts += names.next().name
    }

    val remaining = names.remaining().bits()
    if (remaining != 0uL) {
        parts += "0x" + remaining.toString(16)
    }

    return parts.joinToString(" | ")
}

/**
 * Parse a flags value from text.
 *
 * Names must correspond to defined flags. Hexadecimal values retain unknown
 * bits.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> fromText(input: String, seed: B): B {
    var parsedFlags = seed.empty()
    val trimmed = input.trim()

    if (trimmed.isEmpty()) {
        return parsedFlags
    }

    for (part in input.split('|')) {
        val flag = part.trim()
        if (flag.isEmpty()) {
            throw IllegalArgumentException(ParseError(ParseErrorKind.EMPTY_FLAG).toString())
        }

        val parsedFlag =
            if (flag.startsWith("0x")) {
                val hex = flag.removePrefix("0x")
                val bits = hex.toULongOrNull(16)
                    ?: throw IllegalArgumentException(ParseError(ParseErrorKind.INVALID_HEX_FLAG, hex).toString())
                seed.fromBitsRetain(bits)
            } else {
                seed.fromName(flag)
                    ?: throw IllegalArgumentException(ParseError(ParseErrorKind.INVALID_NAMED_FLAG, flag).toString())
            }

        parsedFlags = parsedFlags.insert(parsedFlag)
    }

    return parsedFlags
}

/**
 * Write a flags value as text, ignoring unknown bits.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> toTextTruncate(flags: B): String = toText(flags.fromBitsTruncate(flags.bits()))

/**
 * Parse a flags value from text and discard unknown bits.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> fromTextTruncate(input: String, seed: B): B =
    seed.fromBitsTruncate(fromText(input, seed).bits())

/**
 * Write only contained, defined, named flags in a flags value as text.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> toTextStrict(flags: B): String {
    val parts = mutableListOf<String>()
    val names = flags.iterNames()

    while (names.hasNext()) {
        parts += names.next().name
    }

    return parts.joinToString(" | ")
}

/**
 * Parse a flags value from names only.
 */
@HiddenFromObjC
public fun <B : BitFlags<B>> fromTextStrict(input: String, seed: B): B {
    var parsedFlags = seed.empty()
    val trimmed = input.trim()

    if (trimmed.isEmpty()) {
        return parsedFlags
    }

    for (part in input.split('|')) {
        val flag = part.trim()
        if (flag.isEmpty()) {
            throw IllegalArgumentException(ParseError(ParseErrorKind.EMPTY_FLAG).toString())
        }

        if (flag.startsWith("0x")) {
            throw IllegalArgumentException(
                ParseError(ParseErrorKind.INVALID_HEX_FLAG, "unsupported hex flag value").toString(),
            )
        }

        val parsedFlag = seed.fromName(flag)
            ?: throw IllegalArgumentException(ParseError(ParseErrorKind.INVALID_NAMED_FLAG, flag).toString())
        parsedFlags = parsedFlags.insert(parsedFlag)
    }

    return parsedFlags
}
