package sh.writelog.backend.user.application.services

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import sh.writelog.backend.user.application.port.inbound.ResignCommand
import sh.writelog.backend.user.application.port.inbound.ResignException
import sh.writelog.backend.user.application.port.outbound.LoadUserPort
import sh.writelog.backend.user.application.port.outbound.RemoveUserPort
import sh.writelog.backend.user.domain.User
import sh.writelog.backend.user.domain.UserId

@DisplayName("회원탈퇴 서비스 명세")
internal class ResignServiceTest : FunSpec({
    lateinit var loadUserPort: LoadUserPort
    lateinit var removeUserPort: RemoveUserPort
    lateinit var uut: ResignService

    beforeTest {
        loadUserPort = mockk(relaxed = true)
        removeUserPort = mockk(relaxed = true)
        uut = ResignService(loadUserPort, removeUserPort)
    }

    context("실행 규칙") {
        test("존재하지 않는 사용자가 회원탈퇴를 시도하는 경우 UserNotFound 를 throw 한다.") {
            val userId = "test-user-id"
            every { loadUserPort.loadById(UserId(userId)) } returns null

            shouldThrow<ResignException.UserNotFound> {
                uut.execute(ResignCommand(userId = userId))
            }
        }

        test("User ID 를 통해 회원탈퇴를 한다.") {
            val userId = "test-user-id"
            every { loadUserPort.loadById(UserId(userId)) } returns mockk<User>().apply {
                every { id } returns UserId(userId)
            }

            uut.execute(ResignCommand(userId = userId))

            verify(exactly = 1) {
                removeUserPort.removeById(UserId(userId))
            }
        }
    }
})