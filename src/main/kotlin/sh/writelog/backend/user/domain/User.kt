package sh.writelog.backend.user.domain

import java.time.LocalDateTime
import java.util.UUID

data class UserId(
    val id: String = UUID.randomUUID().toString()
)

class User(
    val id: UserId,
    val nickname: Nickname,
    val email: Email,
    val profileImageUrl: String,
    val bio: String,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime
) {
    companion object {
        fun createNew(
            nickname: Nickname,
            email: Email,
            profileImageUrl: String? = null,
            bio: String? = null
        ): User {
            val now = LocalDateTime.now()
            return User(
                id = UserId(),
                nickname = nickname,
                email = email,
                profileImageUrl = profileImageUrl ?: "",
                bio = bio ?: "",
                createdAt = now,
                lastModifiedAt = now
            )
        }

        fun create(
            id: UserId,
            nickname: Nickname,
            email: Email,
            profileImageUrl: String,
            bio: String,
            createdAt: LocalDateTime,
            lastModifiedAt: LocalDateTime
        ): User {
            return User(
                id = id,
                nickname = nickname,
                email = email,
                profileImageUrl = profileImageUrl,
                bio = bio,
                createdAt = createdAt,
                lastModifiedAt = lastModifiedAt
            )
        }
    }
}