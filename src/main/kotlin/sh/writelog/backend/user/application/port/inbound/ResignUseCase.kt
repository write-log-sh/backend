package sh.writelog.backend.user.application.port.inbound

import kotlin.jvm.Throws

interface ResignUseCase {
    @Throws(ResignException.UserNotFound::class)
    fun execute(command: ResignCommand)
}

data class ResignCommand(
    val userId: String
)

sealed class ResignException(message: String) : RuntimeException(message) {
    object UserNotFound: ResignException(
        "사용자가 존재하지 않습니다."
    )
}
