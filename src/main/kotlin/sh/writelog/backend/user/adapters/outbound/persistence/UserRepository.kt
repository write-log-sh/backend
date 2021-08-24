package sh.writelog.backend.user.adapters.outbound.persistence

import sh.writelog.backend.user.domain.Email
import sh.writelog.backend.user.domain.User

interface UserRepository {
    fun findByEmail(email: Email): User?

    fun save(user: User)
}