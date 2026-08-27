// port-lint: tests bitflags/src/src/tests/union.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class UnionTest {
    @Test
    public fun cases() {
        case(
            TestFlags.empty(),
            listOf(
                Pair(TestFlags.A, 1uL),
                Pair(TestFlags.all(), 1uL or (1uL shl 1) or (1uL shl 2)),
                Pair(TestFlags.empty(), 0uL),
                Pair(TestFlags.fromBitsRetain(1uL shl 3), 1uL shl 3),
            )
        )

        case(
            TestFlags.A or TestFlags.C,
            listOf(
                Pair(TestFlags.A or TestFlags.B, 1uL or (1uL shl 1) or (1uL shl 2)),
                Pair(TestFlags.A, 1uL or (1uL shl 2)),
            )
        )
    }

    private fun <T : BitFlags<T>> case(value: T, inputs: List<Pair<T, ULong>>) {
        for ((input, expected) in inputs) {
            assertEquals(expected, value.union(input).bits(), "$value.union($input)")
            assertEquals(expected, (value or input).bits(), "$value or $input")
        }
    }
}
