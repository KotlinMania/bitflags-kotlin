// port-lint: tests bitflags/src/src/tests/symmetric_difference.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class SymmetricDifferenceTest {
    @Test
    public fun cases() {
        case(
            TestFlags.empty(),
            listOf(
                Pair(TestFlags.empty(), 0uL),
                Pair(TestFlags.all(), 1uL or (1uL shl 1) or (1uL shl 2)),
                Pair(TestFlags.fromBitsRetain(1uL shl 3), 1uL shl 3),
            ),
        )

        case(
            TestFlags.A,
            listOf(
                Pair(TestFlags.empty(), 1uL),
                Pair(TestFlags.A, 0uL),
                Pair(TestFlags.all(), (1uL shl 1) or (1uL shl 2)),
            ),
        )

        case(
            TestFlags.A or TestFlags.B or TestFlags.fromBitsRetain(1uL shl 3),
            listOf(
                Pair(TestFlags.ABC, (1uL shl 2) or (1uL shl 3)),
                Pair(TestFlags.fromBitsRetain(1uL shl 3), 1uL or (1uL shl 1)),
            ),
        )
    }

    private fun <T : BitFlags<T>> case(value: T, inputs: List<Pair<T, ULong>>) {
        for ((input, expected) in inputs) {
            assertEquals(expected, value.symmetricDifference(input).bits(), "$value.symmetricDifference($input)")
            assertEquals(expected, (value xor input).bits(), "$value xor $input")
            assertEquals(expected, value.toggle(input).bits(), "$value.toggle($input)")
        }
    }
}
