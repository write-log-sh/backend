package sh.writelog.backend.user.domain

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.time.withConstantNow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDateTime

@DisplayName("사용자 명세")
internal class UserTest : FunSpec({
    context("생성") {
        context("create") {
            test("사용자는 ID, 닉네임, 이메일, 프로파일 이미지 URL, bio, 회원가입 시각," +
                " 마지막 정보 변경 시각을 이용해 생성할 수 있다.") {
                val userId = UserId("test-user-id")
                val nickname = Nickname("test-nickname")
                val email = Email("test@email.com")
                val profileImageUrl = "test-url"
                val bio = "test-bio"
                val createdAt = LocalDateTime.of(2021, 8, 23, 23, 0)
                val lastModifiedAt = LocalDateTime.of(2021, 8, 23, 23, 0)
                val uut = User.create(
                    id = userId,
                    nickname = nickname,
                    email = email,
                    profileImageUrl = profileImageUrl,
                    bio = bio,
                    createdAt = createdAt,
                    lastModifiedAt = lastModifiedAt,
                )

                uut.id shouldBe userId
                uut.nickname shouldBe nickname
                uut.email shouldBe email
                uut.profileImageUrl shouldBe profileImageUrl
                uut.bio shouldBe bio
                uut.createdAt shouldBe createdAt
                uut.lastModifiedAt shouldBe lastModifiedAt
            }
        }

        context("createNew") {
            val nickname = Nickname("test-nickname")
            val email = Email("test@email.com")
            val now = LocalDateTime.of(2021, 8, 23, 23, 0)

            test("사용자는 닉네임, 이메일, 프로파일 이미지 URL, bio를 이용해 생성할 수 있다.") {
                val profileImageUrl = "test-url"
                val bio = "test-bio"
                val uut = withConstantNow(now) {
                    User.createNew(
                        nickname = nickname,
                        email = email,
                        profileImageUrl = profileImageUrl,
                        bio = bio
                    )
                }

                uut.id shouldNotBe null
                uut.nickname shouldBe nickname
                uut.email shouldBe email
                uut.profileImageUrl shouldBe profileImageUrl
                uut.bio shouldBe bio
                uut.createdAt shouldBe now
                uut.lastModifiedAt shouldBe now
            }

            test("사용자는 닉네임, 이메일을 이용해 생성할 수 있다.") {
                val uut = withConstantNow(now) {
                    User.createNew(
                        nickname = nickname,
                        email = email,
                    )
                }

                uut.id shouldNotBe null
                uut.nickname shouldBe nickname
                uut.email shouldBe email
                uut.profileImageUrl.isEmpty() shouldBe true
                uut.bio.isEmpty() shouldBe true
                uut.createdAt shouldBe now
                uut.lastModifiedAt shouldBe now
            }
        }
    }
})