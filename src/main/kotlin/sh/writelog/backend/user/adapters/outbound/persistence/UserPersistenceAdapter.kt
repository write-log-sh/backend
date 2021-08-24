package sh.writelog.backend.user.adapters.outbound.persistence

import sh.writelog.backend.shared.PersistenceAdapter
import sh.writelog.backend.user.application.port.outbound.LoadUserPort
import sh.writelog.backend.user.application.port.outbound.SaveUserPort
import sh.writelog.backend.user.domain.Email
import sh.writelog.backend.user.domain.User

@PersistenceAdapter
class UserPersistenceAdapter(
    private val repository: UserRepository
): SaveUserPort, LoadUserPort {
    override fun loadByEmail(email: Email): User? {
        return repository.findByEmail(email)
    }

    override fun save(user: User) {
        return repository.save(user)
    }
}
