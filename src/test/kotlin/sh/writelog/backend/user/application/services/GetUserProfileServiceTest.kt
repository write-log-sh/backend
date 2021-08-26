package sh.writelog.backend.user.application.services

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import sh.writelog.backend.user.application.port.inbound.GetUserProfileException
import sh.writelog.backend.user.application.port.inbound.GetUserProfileQuery
import sh.writelog.backend.user.application.port.outbound.LoadUserPort
import sh.writelog.backend.user.domain.Email
import sh.writelog.backend.user.domain.Nickname
import sh.writelog.backend.user.domain.User
import sh.writelog.backend.user.domain.UserId

@DisplayName("GetUserProfileService 테스트")
internal class GetUserProfileServiceTest : FunSpec({
    lateinit var loadUserPort: LoadUserPort
    lateinit var uut: GetUserProfileService

    beforeTest {
        loadUserPort = mockk(relaxed = true)
        uut = GetUserProfileService(loadUserPort)
    }

    context("실행 규칙") {
        val userId = "test-user-id"

        test("사용자를 찾을 수 없는 경우 UserNotFound 를 throw 한다.") {
            every { loadUserPort.loadById(UserId(userId)) } returns null

            shouldThrow<GetUserProfileException.UserNotFound> {
                uut.execute(query = GetUserProfileQuery(userId = userId))
            }
        }

        test("사용자 프로필을 조회 한다.") {
            val bio = "test-bio"
            val nickname = Nickname("test-nickname")
            val email = Email("test@email.com")
            val imageUrl = "test-image-url"
            every { loadUserPort.loadById(UserId(userId)) } returns mockk<User>().apply {
                every { bio() } returns bio
                every { nickname() } returns nickname
                every { email() } returns email
                every { profileImageUrl() } returns imageUrl
            }

            val result = uut.execute(query = GetUserProfileQuery(userId = userId))

            result.bio shouldBe bio
            result.nickname shouldBe nickname.value
            result.email shouldBe email.value
            result.profileImageUrl shouldBe imageUrl
        }
    }
})