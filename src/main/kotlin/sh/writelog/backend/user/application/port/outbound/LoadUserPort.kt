package sh.writelog.backend.user.application.port.outbound

import sh.writelog.backend.user.domain.Email
import sh.writelog.backend.user.domain.User

interface LoadUserPort {
    fun loadByEmail(email: Email): User?
}