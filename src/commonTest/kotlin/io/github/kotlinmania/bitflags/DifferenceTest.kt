// port-lint: tests src/tests/difference.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertEquals

public class DifferenceTest {
    @Test
    public fun cases() {
        case(
            TestFlags.A or TestFlags.B,
            listOf(
                Pair(TestFlags.A, 1uL shl 1),
                Pair(TestFlags.B, 1uL),
                Pair(TestFlags.fromBitsRetain(1uL shl 3), 1uL or (1uL shl 1)),
            ),
        )

        case(
            TestFlags.fromBitsRetain(1uL or (1uL shl 3)),
            listOf(
                Pair(TestFlags.A, 1uL shl 3),
                Pair(TestFlags.fromBitsRetain(1uL shl 3), 1uL),
            ),
        )

        case(
            TestExternal.fromBitsRetain(0xFFuL),
            listOf(
                Pair(TestExternal.A, 0xFEuL),
            ),
        )

        assertEquals(
            0xFEuL,
            (TestExternal.fromBitsRetain(0xFFuL) and !TestExternal.A).bits(),
        )

        assertEquals(
            0xFEuL,
            (TestFlags.fromBitsRetain(0xFFuL).difference(TestFlags.A)).bits(),
        )

        assertEquals(
            (1uL shl 1) or (1uL shl 2),
            (TestFlags.fromBitsRetain(0xFFuL) and !TestFlags.A).bits(),
        )
    }

    private fun <T : BitFlags<T>> case(value: T, inputs: List<Pair<T, ULong>>) {
        for ((input, expected) in inputs) {
            assertEquals(expected, value.difference(input).bits(), "$value.difference($input)")
            assertEquals(expected, (value - input).bits(), "$value - $input")
        }
    }
}
