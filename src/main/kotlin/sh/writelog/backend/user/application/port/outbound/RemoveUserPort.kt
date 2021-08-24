package sh.writelog.backend.user.application.port.outbound

import sh.writelog.backend.user.domain.UserId

interface RemoveUserPort {
    fun removeById(userId: UserId)
}