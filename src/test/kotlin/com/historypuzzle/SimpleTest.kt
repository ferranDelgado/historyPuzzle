package com.historypuzzle

import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object SimpleTest : Spek({
    describe("Post new card") {
        it("should be equal") {
            3 shouldBeEqualTo 3
        }
    }
})