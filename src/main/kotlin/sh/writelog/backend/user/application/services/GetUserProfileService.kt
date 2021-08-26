package sh.writelog.backend.user.application.services

import org.springframework.stereotype.Service
import sh.writelog.backend.user.application.port.inbound.GetUserProfileException
import sh.writelog.backend.user.application.port.inbound.GetUserProfileQuery
import sh.writelog.backend.user.application.port.inbound.GetUserProfileQueryResult
import sh.writelog.backend.user.application.port.inbound.GetUserProfileUseCase
import sh.writelog.backend.user.application.port.inbound.ResignCommand
import sh.writelog.backend.user.application.port.inbound.ResignException
import sh.writelog.backend.user.application.port.inbound.ResignUseCase
import sh.writelog.backend.user.application.port.outbound.LoadUserPort
import sh.writelog.backend.user.application.port.outbound.RemoveUserPort
import sh.writelog.backend.user.domain.UserId

@Service
class GetUserProfileService(
    private val loadUserPort: LoadUserPort,
) : GetUserProfileUseCase {
    override fun execute(query: GetUserProfileQuery): GetUserProfileQueryResult {
        val user = findUser(query)
        return GetUserProfileQueryResult(
            email = user.email().value,
            nickname = user.nickname().value,
            profileImageUrl = user.profileImageUrl(),
            bio = user.bio()
        )
    }

    private fun findUser(query: GetUserProfileQuery) =
        loadUserPort.loadById(UserId(query.userId))
            ?: throw GetUserProfileException.UserNotFound
}
