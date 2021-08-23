package sh.writelog.backend.user.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

@DisplayName("닉네임 명세")
internal class NicknameTest : FunSpec({
    context("생성") {
        test("닉네임은 빈 값일 수 없다.") {
            shouldThrow<IllegalArgumentException> {
                Nickname("")
            }
        }

        test("닉네임은 길이 1 이상의 문자열로 생성될 수 있다.") {
            val uut = Nickname("a")

            uut.value shouldBe "a"
        }
    }
})