package sh.writelog.backend.user.application.services

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import sh.writelog.backend.user.application.port.inbound.JoinCommand
import sh.writelog.backend.user.application.port.inbound.JoinException
import sh.writelog.backend.user.application.port.outbound.LoadUserPort
import sh.writelog.backend.user.application.port.outbound.SaveUserPort
import sh.writelog.backend.user.domain.Email
import sh.writelog.backend.user.domain.Nickname
import sh.writelog.backend.user.domain.User

@DisplayName("회원가입 서비스 명세")
internal class JoinServiceTest: FunSpec({
    lateinit var loadUserPort: LoadUserPort
    lateinit var saveUserPort: SaveUserPort
    lateinit var uut: JoinService

    beforeTest {
        loadUserPort = mockk(relaxed = true)
        saveUserPort = mockk(relaxed = true)
        uut = JoinService(loadUserPort, saveUserPort)
    }

    context("실행 규칙") {
        test("이미 존재하는 이메일로 회원가입하는 경우 EmailAlreadyExists 를 throw 한다.") {
            val email = "test@email.com"
            val nickname = "test-nickname"
            val command = JoinCommand(
                email = email,
                nickname = nickname,
                profileImageUrl = null,
                bio = null
            )
            every { loadUserPort.loadByEmail(email = Email(email)) } returns mockk()

            shouldThrow<JoinException.EmailAlreadyExists> {
                uut.execute(command)
            }
        }

        test("이메일과 닉네임으로 회원가입을 할 수 있다.") {
            val email = "test@email.com"
            val nickname = "test-nickname"
            val command = JoinCommand(
                email = email,
                nickname = nickname,
                profileImageUrl = null,
                bio = null
            )
            every { loadUserPort.loadByEmail(email = Email(email)) } returns null

            uut.execute(command)

            val slot = slot<User>()
            verify {
                saveUserPort.save(capture(slot))
            }
            with(slot) {
                captured.email() shouldBe Email(email)
                captured.nickname() shouldBe Nickname(nickname)
                captured.profileImageUrl() shouldBe ""
                captured.bio() shouldBe ""
            }
        }

        test("이메일, 닉네임, 프로파일 Url, bio 로 회원가입을 할 수 있다.") {
            val email = "test@email.com"
            val nickname = "test-nickname"
            val profileImageUrl = "test-profile-url"
            val bio = "test-bio"
            val command = JoinCommand(
                email = email,
                nickname = nickname,
                profileImageUrl = profileImageUrl,
                bio = bio
            )
            every { loadUserPort.loadByEmail(email = Email(email)) } returns null

            uut.execute(command)

            val slot = slot<User>()
            verify {
                saveUserPort.save(capture(slot))
            }
            with(slot) {
                captured.email() shouldBe Email(email)
                captured.nickname() shouldBe Nickname(nickname)
                captured.profileImageUrl() shouldBe profileImageUrl
                captured.bio() shouldBe bio
            }
        }
    }
})
