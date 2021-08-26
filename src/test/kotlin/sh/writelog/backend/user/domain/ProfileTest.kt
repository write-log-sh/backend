package sh.writelog.backend.user.domain

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

@DisplayName("사용자 프로필 명세")
internal class ProfileTest: FunSpec({
    context("생성 규칙") {
        val nickname = Nickname("test-nickname")
        val email = Email("test@email.com")
        val imageUrl = "test-url"
        val bio = "test-bio"

        test("닉네임과 이메일을 이용해 프로필을 생성할 수 있다.") {
            val uut = Profile(
                nickname = nickname,
                email = email
            )

            uut.nickname shouldBe nickname
            uut.email shouldBe email
            uut.imageUrl shouldBe null
            uut.bio shouldBe null
        }

        test("닉네임, 이메일, 이미지 URL, bio를 이용해 프로필을 생성할 수 있다.") {
            val uut = Profile(
                nickname = nickname,
                email = email,
                imageUrl = imageUrl,
                bio = bio
            )

            uut.nickname shouldBe nickname
            uut.email shouldBe email
            uut.imageUrl shouldBe imageUrl
            uut.bio shouldBe bio
        }
    }
})