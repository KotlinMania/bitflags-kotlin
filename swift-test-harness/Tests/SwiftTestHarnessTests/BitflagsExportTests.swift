import Testing
import Bitflags

@Suite("Bitflags Swift Export Tests")
struct BitflagsExportTests {
    @Test("Bitflags swift module imported cleanly and enum behaves as expected")
    func swiftModuleLoads() {
        let kind = ParseErrorKind.EMPTY_FLAG
        #expect(kind.description == "EMPTY_FLAG")
    }
}
