// port-lint: tests src/tests/insert.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class InsertTest {
    @Test
    public fun cases() {
        case(
            TestFlags.empty(),
            listOf(
                Pair(TestFlags.A, 1uL),
                Pair(TestFlags.A or TestFlags.B, 1uL or (1uL shl 1)),
                Pair(TestFlags.empty(), 0uL),
                Pair(TestFlags.fromBitsRetain(1uL shl 3), 1uL shl 3),
            )
        )

        case(
            TestFlags.A,
            listOf(
                Pair(TestFlags.A, 1uL),
                Pair(TestFlags.empty(), 1uL),
                Pair(TestFlags.B, 1uL or (1uL shl 1)),
            )
        )
    }

    private fun <T : BitFlags<T>> case(value: T, inputs: List<Pair<T, ULong>>) {
        for ((input, expected) in inputs) {
            assertEquals(expected, value.insert(input).bits(), "$value.insert($input)")
            assertEquals(expected, value.set(input, true).bits(), "$value.set($input, true)")
        }
    }
}
