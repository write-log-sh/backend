package sh.writelog.backend.post.domain

import java.time.LocalDateTime

class Post private constructor(
    val authorId: AuthorId,
    val postId: PostId,
    var title: String,
    var content: String,
    val createdAt: LocalDateTime,
    var lastModifiedAt: LocalDateTime,
    val comments: List<Comment>,
) {
    companion object {
        fun createNew(
            authorId: AuthorId,
            title: String,
            content: String,
        ): Post {
            val now = LocalDateTime.now()
            return Post(
                authorId = authorId,
                postId = PostId(),
                title = title,
                content = content,
                createdAt = now,
                lastModifiedAt = now,
                comments = emptyList(),
            )
        }

        fun create(
            authorId: AuthorId,
            postId: PostId,
            title: String,
            content: String,
            createdAt: LocalDateTime,
            lastModifiedAt: LocalDateTime,
            comments: List<Comment>,
        ): Post {
            return Post(
                authorId = authorId,
                postId = postId,
                title = title,
                content = content,
                createdAt = createdAt,
                lastModifiedAt = lastModifiedAt,
                comments = comments,
            )
        }
    }

    init {
        if (createdAt > lastModifiedAt) {
            throw IllegalArgumentException("생성 시각은 수정 시각보다 이후일 수 없습니다.")
        }
        validateTitleAndContent(
            title = title,
            content = content
        )
    }

    fun update(command: UpdatePostCommand) {
        val titleToUpdate = command.title ?: this.title
        val contentToUpdate = command.content ?: this.content

        validateTitleAndContent(
            title = titleToUpdate,
            content = contentToUpdate
        )

        this.title = titleToUpdate
        this.content = contentToUpdate
        this.lastModifiedAt = LocalDateTime.now()
    }

    private fun validateTitleAndContent(
        title: String,
        content: String,
    ) {
        if (title.isEmpty() || content.isEmpty()) {
            throw IllegalArgumentException("제목이나 내용은 빈 문자열일 수 없습니다.")
        }
    }
}
