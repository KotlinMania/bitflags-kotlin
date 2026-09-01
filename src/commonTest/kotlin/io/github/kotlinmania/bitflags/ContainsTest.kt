// port-lint: tests tests/contains.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class ContainsTest {
    @Test
    public fun cases() {
        case(
            TestFlags.empty(),
            listOf(
                Pair(TestFlags.empty(), true),
                Pair(TestFlags.A, false),
                Pair(TestFlags.B, false),
                Pair(TestFlags.C, false),
                Pair(TestFlags.fromBitsRetain(1uL shl 3), false),
            ),
        )

        case(
            TestFlags.A,
            listOf(
                Pair(TestFlags.empty(), true),
                Pair(TestFlags.A, true),
                Pair(TestFlags.B, false),
                Pair(TestFlags.C, false),
                Pair(TestFlags.ABC, false),
                Pair(TestFlags.fromBitsRetain(1uL shl 3), false),
                Pair(TestFlags.fromBitsRetain(1uL or (1uL shl 3)), false),
            ),
        )

        case(
            TestFlags.ABC,
            listOf(
                Pair(TestFlags.empty(), true),
                Pair(TestFlags.A, true),
                Pair(TestFlags.B, true),
                Pair(TestFlags.C, true),
                Pair(TestFlags.ABC, true),
                Pair(TestFlags.fromBitsRetain(1uL shl 3), false),
            ),
        )

        case(
            TestFlags.fromBitsRetain(1uL shl 3),
            listOf(
                Pair(TestFlags.empty(), true),
                Pair(TestFlags.A, false),
                Pair(TestFlags.B, false),
                Pair(TestFlags.C, false),
                Pair(TestFlags.fromBitsRetain(1uL shl 3), true),
            ),
        )

        case(
            TestZero.ZERO,
            listOf(
                Pair(TestZero.ZERO, true),
            ),
        )

        case(
            TestOverlapping.AB,
            listOf(
                Pair(TestOverlapping.AB, true),
                Pair(TestOverlapping.BC, false),
                Pair(TestOverlapping.fromBitsRetain(1uL shl 1), true),
            ),
        )

        case(
            TestExternal.all(),
            listOf(
                Pair(TestExternal.A, true),
                Pair(TestExternal.B, true),
                Pair(TestExternal.C, true),
                Pair(TestExternal.fromBitsRetain((1uL shl 5) or (1uL shl 7)), true),
            ),
        )
    }

    private fun <T : BitFlags<T>> case(value: T, inputs: List<Pair<T, Boolean>>) {
        for ((input, expected) in inputs) {
            assertEquals(expected, value.contains(input), "$value.contains($input)")
        }
    }
}
