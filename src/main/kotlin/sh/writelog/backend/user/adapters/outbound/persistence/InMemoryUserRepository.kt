package sh.writelog.backend.user.adapters.outbound.persistence

import org.springframework.stereotype.Repository
import sh.writelog.backend.user.domain.Email
import sh.writelog.backend.user.domain.User
import sh.writelog.backend.user.domain.UserId

@Repository
class InMemoryUserRepository : UserRepository {
    private val users = HashMap<UserId, User>()
    override fun findById(id: UserId): User? {
        return users[id]
    }

    override fun findByEmail(email: Email): User? {
        return users.values.find { it.email() == email }
    }

    override fun save(user: User) {
        users[user.id] = user
    }

    override fun remove(id: UserId) {
        users.remove(id)
    }
}
