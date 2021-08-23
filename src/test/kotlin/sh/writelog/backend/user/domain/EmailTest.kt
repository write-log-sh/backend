package sh.writelog.backend.user.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

@DisplayName("이메일 명세")
internal class EmailTest: FunSpec({
    context("생성") {
        test("이메일은 유효하지 않은 문자열로 생성할 수 없다.") {
            shouldThrow<IllegalArgumentException> {
                Email("a@")
            }
        }

        test("이메일은 유효한 이메일 문자열로 생성될 수 있다.") {
            val uut = Email("a@b.com")

            uut.value shouldBe "a@b.com"
        }
    }
})
