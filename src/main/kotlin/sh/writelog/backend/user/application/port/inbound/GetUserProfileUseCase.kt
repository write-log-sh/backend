package sh.writelog.backend.user.application.port.inbound

import kotlin.jvm.Throws

interface GetUserProfileUseCase {
    @Throws(GetUserProfileException.UserNotFound::class)
    fun execute(query: GetUserProfileQuery): GetUserProfileQueryResult
}

data class GetUserProfileQuery(
    val userId: String
)

data class GetUserProfileQueryResult(
    val email: String,
    val nickname: String,
    val profileImageUrl: String?,
    val bio: String?,
)

sealed class GetUserProfileException(message: String) : RuntimeException(message) {
    object UserNotFound: GetUserProfileException(
        "사용자를 찾을 수 없습니다. 이메일입니다."
    )
}
