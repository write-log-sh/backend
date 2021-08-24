package sh.writelog.backend.user.application.port.outbound

import sh.writelog.backend.user.domain.User

interface SaveUserPort {
    fun save(user: User)
}