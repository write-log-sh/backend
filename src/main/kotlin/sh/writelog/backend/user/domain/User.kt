package sh.writelog.backend.user.domain

import java.time.LocalDateTime
import java.util.UUID

data class UserId(
    val id: String = UUID.randomUUID().toString()
)

class User(
    val id: UserId,
    val profile: Profile,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime
) {
    companion object {
        fun createNew(profile: Profile): User {
            val now = LocalDateTime.now()
            return User(
                id = UserId(),
                profile = profile,
                createdAt = now,
                lastModifiedAt = now
            )
        }

        fun create(
            id: UserId,
            profile: Profile,
            createdAt: LocalDateTime,
            lastModifiedAt: LocalDateTime
        ): User {
            return User(
                id = id,
                profile = profile,
                createdAt = createdAt,
                lastModifiedAt = lastModifiedAt
            )
        }
    }

    fun nickname(): Nickname {
        return profile.nickname
    }

    fun email(): Email {
        return profile.email
    }

    fun profileImageUrl(): String? {
        return profile.imageUrl
    }

    fun bio(): String? {
        return profile.bio
    }
}