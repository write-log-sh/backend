package sh.writelog.backend.post.domain

data class AuthorId(val value: String)

data class Author(
    val id: AuthorId,
    val name: String
)
