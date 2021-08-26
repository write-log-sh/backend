package sh.writelog.backend.user.domain

data class Profile(
    val nickname: Nickname,
    val email: Email,
    val imageUrl: String? = null,
    val bio: String? = null
)
