package sh.writelog.backend.user.application.port.outbound

import sh.writelog.backend.user.domain.Email
import sh.writelog.backend.user.domain.User
import sh.writelog.backend.user.domain.UserId

interface LoadUserPort {
    fun loadById(id: UserId): User?

    fun loadByEmail(email: Email): User?
}