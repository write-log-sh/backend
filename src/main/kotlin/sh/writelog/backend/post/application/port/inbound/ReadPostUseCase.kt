package sh.writelog.backend.post.application.port.inbound

import java.time.LocalDateTime
import kotlin.jvm.Throws

interface ReadPostUseCase {
    @Throws(
        ReadPostException.PostNotFound::class,
        ReadPostException.AuthorNotFound::class
    )
    fun execute(query: ReadPostQuery): PostQueryResult
}

data class ReadPostQuery(val postId: String)

data class PostQueryResult(
    val authorName: String,
    var title: String,
    var content: String,
    val createdAt: LocalDateTime,
    var lastModifiedAt: LocalDateTime,
    val comments: List<CommentQueryResult>,
)

data class CommentQueryResult(
    val comment: String,
)

sealed class ReadPostException(message: String) : RuntimeException(message) {
    object PostNotFound: ReadPostException(
        "포스트를 찾을 수 없습니다."
    )

    object AuthorNotFound: ReadPostException(
        "작성자를 찾을 수 없습니다."
    )
}
