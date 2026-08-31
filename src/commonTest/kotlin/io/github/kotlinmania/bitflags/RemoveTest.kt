// port-lint: tests bitflags/src/src/tests/remove.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class RemoveTest {
    @Test
    public fun cases() {
        case(
            TestFlags.empty(),
            listOf(
                Pair(TestFlags.A, 0uL),
                Pair(TestFlags.empty(), 0uL),
                Pair(TestFlags.fromBitsRetain(1uL shl 3), 0uL),
            ),
        )

        case(
            TestFlags.A,
            listOf(
                Pair(TestFlags.A, 0uL),
                Pair(TestFlags.empty(), 1uL),
                Pair(TestFlags.B, 1uL),
            ),
        )

        case(
            TestFlags.ABC,
            listOf(
                Pair(TestFlags.A, (1uL shl 1) or (1uL shl 2)),
                Pair(TestFlags.A or TestFlags.C, 1uL shl 1),
            ),
        )
    }

    private fun <T : BitFlags<T>> case(value: T, inputs: List<Pair<T, ULong>>) {
        for ((input, expected) in inputs) {
            assertEquals(expected, value.remove(input).bits(), "$value.remove($input)")
            assertEquals(expected, value.set(input, false).bits(), "$value.set($input, false)")
        }
    }
}
