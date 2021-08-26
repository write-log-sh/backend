package sh.writelog.backend.user.domain

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.time.withConstantNow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDateTime

@DisplayName("사용자 명세")
internal class UserTest : FunSpec({
    val nickname = Nickname("test-nickname")
    val email = Email("test@email.com")
    val imageUrl = "test-url"
    val bio = "test-bio"
    val profile = Profile(
        nickname = nickname,
        email = email,
        imageUrl = imageUrl,
        bio = bio
    )

    context("생성") {
        context("create") {
            test("사용자는 ID, 프로필, 회원가입 시각 마지막 정보 변경 시각을 이용해 생성할 수 있다.") {
                val userId = UserId("test-user-id")
                val createdAt = LocalDateTime.of(2021, 8, 23, 23, 0)
                val lastModifiedAt = LocalDateTime.of(2021, 8, 23, 23, 0)
                val uut = User.create(
                    id = userId,
                    profile = profile,
                    createdAt = createdAt,
                    lastModifiedAt = lastModifiedAt,
                )

                uut.id shouldBe userId
                uut.profile shouldBe profile
                uut.createdAt shouldBe createdAt
                uut.lastModifiedAt shouldBe lastModifiedAt
            }
        }

        context("createNew") {
            val now = LocalDateTime.of(2021, 8, 23, 23, 0)

            test("사용자는 프로필을 이용해 생성할 수 있다.") {
                val uut = withConstantNow(now) {
                    User.createNew(profile = profile)
                }

                uut.id shouldNotBe null
                uut.profile shouldBe profile
                uut.createdAt shouldBe now
                uut.lastModifiedAt shouldBe now
            }
        }
    }

    context("nickname()") {
        test("닉네임을 반환한다.") {
            User.createNew(profile = profile).nickname() shouldBe nickname
        }
    }

    context("email()") {
        test("이메일을 반환한다.") {
            User.createNew(profile = profile).email() shouldBe email
        }
    }

    context("profileImageUrl()") {
        test("프로필 이미지를 반환한다.") {
            User.createNew(profile = profile).profileImageUrl() shouldBe imageUrl
        }
    }

    context("bio()") {
        test("bio를 반환한다.") {
            User.createNew(profile = profile).bio() shouldBe bio
        }
    }
})