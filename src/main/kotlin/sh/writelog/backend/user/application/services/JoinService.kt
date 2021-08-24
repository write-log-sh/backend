package sh.writelog.backend.user.application.services

import org.springframework.stereotype.Service
import sh.writelog.backend.user.application.port.inbound.JoinCommand
import sh.writelog.backend.user.application.port.inbound.JoinException
import sh.writelog.backend.user.application.port.inbound.JoinUseCase
import sh.writelog.backend.user.application.port.outbound.LoadUserPort
import sh.writelog.backend.user.application.port.outbound.SaveUserPort
import sh.writelog.backend.user.domain.Email
import sh.writelog.backend.user.domain.Nickname
import sh.writelog.backend.user.domain.User

@Service
class JoinService(
    private val loadUserPort: LoadUserPort,
    private val saveUserPort: SaveUserPort,
) : JoinUseCase {
    override fun execute(command: JoinCommand) {
        val email = Email(command.email)
        val user = loadUserPort.loadByEmail(email)

        checkEmailAlreadyExists(user)

        val newUser = User.createNew(
            nickname = Nickname(command.nickname),
            email = email,
            profileImageUrl = command.profileImageUrl,
            bio = command.bio
        )
        saveUserPort.save(newUser)
    }

    private fun checkEmailAlreadyExists(user: User?) {
        if (user != null) {
            throw JoinException.EmailAlreadyExists
        }
    }

}