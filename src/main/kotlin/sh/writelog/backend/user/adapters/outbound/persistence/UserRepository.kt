package sh.writelog.backend.user.adapters.outbound.persistence

import sh.writelog.backend.user.domain.Email
import sh.writelog.backend.user.domain.User
import sh.writelog.backend.user.domain.UserId

interface UserRepository {
    fun findById(id: UserId): User?

    fun findByEmail(email: Email): User?

    fun save(user: User)

    fun remove(id: UserId)
}