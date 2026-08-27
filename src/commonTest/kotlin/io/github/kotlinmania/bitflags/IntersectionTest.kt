// port-lint: tests bitflags/src/src/tests/intersection.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class IntersectionTest {
    @Test
    public fun cases() {
        case(
            TestFlags.empty(),
            listOf(
                Pair(TestFlags.empty(), 0uL),
                Pair(TestFlags.all(), 0uL),
            )
        )

        case(
            TestFlags.all(),
            listOf(
                Pair(TestFlags.all(), 1uL or (1uL shl 1) or (1uL shl 2)),
                Pair(TestFlags.A, 1uL),
                Pair(TestFlags.fromBitsRetain(1uL shl 3), 0uL),
            )
        )

        case(
            TestFlags.fromBitsRetain(1uL shl 3),
            listOf(
                Pair(TestFlags.fromBitsRetain(1uL shl 3), 1uL shl 3),
            )
        )

        case(
            TestOverlapping.AB,
            listOf(
                Pair(TestOverlapping.BC, 1uL shl 1),
            )
        )
    }

    private fun <T : BitFlags<T>> case(value: T, inputs: List<Pair<T, ULong>>) {
        for ((input, expected) in inputs) {
            assertEquals(expected, value.intersection(input).bits(), "$value.intersection($input)")
            assertEquals(expected, (value and input).bits(), "$value and $input")
        }
    }
}
