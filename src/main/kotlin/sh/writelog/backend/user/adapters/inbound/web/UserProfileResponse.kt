package sh.writelog.backend.user.adapters.inbound.web

data class UserProfileResponse(
    val email: String,
    val nickname: String,
    val profileImageUrl: String?,
    val bio: String?
)