package sh.writelog.backend.user.application.services

import org.springframework.stereotype.Service
import sh.writelog.backend.user.application.port.inbound.ResignCommand
import sh.writelog.backend.user.application.port.inbound.ResignException
import sh.writelog.backend.user.application.port.inbound.ResignUseCase
import sh.writelog.backend.user.application.port.outbound.LoadUserPort
import sh.writelog.backend.user.application.port.outbound.RemoveUserPort
import sh.writelog.backend.user.domain.UserId

@Service
class ResignService(
    private val loadUserPort: LoadUserPort,
    private val removeUserPort: RemoveUserPort,
) : ResignUseCase {
    override fun execute(command: ResignCommand) {
        val user = findUser(command)
        removeUserPort.removeById(user.id)
    }

    private fun findUser(command: ResignCommand) =
        loadUserPort.loadById(UserId(command.userId)) ?: throw ResignException.UserNotFound
}
