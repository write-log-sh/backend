package sh.writelog.backend.user.application.port.inbound

import kotlin.jvm.Throws

interface JoinUseCase {
    @Throws(JoinException.EmailAlreadyExists::class)
    fun execute(command: JoinCommand)
}

data class JoinCommand(
    val email: String,
    val nickname: String,
    val profileImageUrl: String?,
    val bio: String?,
)

sealed class JoinException(message: String) : RuntimeException(message) {
    object EmailAlreadyExists: JoinException(
        "이미 가입된 이메일입니다."
    )
}
