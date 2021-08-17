package sh.writelog.backend.post.domain

import java.time.LocalDateTime

object PostFixture {
    fun create(
        createdAt: LocalDateTime = LocalDateTime.now(),
        lastModifiedAt: LocalDateTime = LocalDateTime.now(),
        title: String = "test-title",
        content: String = "test-content"
    ): Post {
        val comment1 = "test-comment-1"
        val comment2 = "test-comment-2"
        val comments = listOf(
            Comment(comment1),
            Comment(comment2)
        )
        val postId = PostId("test-post-id")
        val authorId = AuthorId("test-author-id")

        return Post.create(
            authorId = authorId,
            postId = postId,
            title = title,
            content = content,
            createdAt = createdAt,
            lastModifiedAt = lastModifiedAt,
            comments = comments
        )
    }
}