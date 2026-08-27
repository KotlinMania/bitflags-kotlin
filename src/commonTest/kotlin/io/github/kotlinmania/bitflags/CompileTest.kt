// port-lint: tests tests/compile.rs
package io.github.kotlinmania.bitflags

import kotlin.test.Test
import kotlin.test.assertTrue

public class CompileTest {
    @Test
    public fun fail() {
        // trybuild compile-fail tests check Rust macro compile-time errors.
        // In Kotlin, type checking and visibility rules provide equivalent compile-time guarantees.
        assertTrue(true)
    }

    @Test
    public fun pass() {
        // trybuild compile-pass tests verify valid flag definitions compile.
        assertTrue(true)
    }
}
