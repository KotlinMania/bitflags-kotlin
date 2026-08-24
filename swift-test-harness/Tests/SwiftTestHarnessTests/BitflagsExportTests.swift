#if canImport(Testing)
import Testing
import Bitflags

@Suite("Bitflags Swift Export Tests")
struct BitflagsExportTests {
    @Test("Bitflags swift module imported cleanly")
    func swiftModuleLoads() {
        #expect(true, "Bitflags swift module imported cleanly")
    }
}
#elseif canImport(XCTest)
import XCTest
import Bitflags

final class BitflagsExportTests: XCTestCase {
    func testSwiftModuleLoads() throws {
        XCTAssertTrue(true, "Bitflags swift module imported cleanly")
    }
}
#endif
