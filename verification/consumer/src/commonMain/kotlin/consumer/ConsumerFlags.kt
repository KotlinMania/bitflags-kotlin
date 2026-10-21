package consumer

import io.github.kotlinmania.bitflags.BitFlags
import io.github.kotlinmania.bitflags.Flag
import io.github.kotlinmania.bitflags.Flags

public class ConsumerFlags(bits: ULong) : Flags<ConsumerFlags>(bits) {
    override fun flags(): List<Flag<ConsumerFlags>> = listOf(
        Flag.new("A", ConsumerFlags(1uL)),
        Flag.new("B", ConsumerFlags(2uL)),
    )

    override fun fromBitsRetain(bits: ULong): ConsumerFlags = ConsumerFlags(bits)
}

public class LegacyConsumerFlags(bits: ULong) : BitFlags<LegacyConsumerFlags>(bits) {
    override fun flags(): List<Flag<LegacyConsumerFlags>> = listOf(
        Flag.new("A", LegacyConsumerFlags(1uL)),
    )

    override fun fromBitsRetain(bits: ULong): LegacyConsumerFlags = LegacyConsumerFlags(bits)
}
