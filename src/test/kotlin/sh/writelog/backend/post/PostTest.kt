package sh.writelog.backend.post

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.time.withConstantNow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDateTime

internal class PostTest: FunSpec({
    context("초기화") {
        test("포스트를 생성할 수 있다.") {
            val title = "test-title"
            val content = "test-content"
            val createdAt = LocalDateTime.of(2021, 8, 4, 10, 30)
            val lastModifiedAt = LocalDateTime.of(2021, 8, 4, 10, 30)
            val postId = PostId("test-post-id")
            val comment1 = "test-comment-1"
            val comment2 = "test-comment-2"
            val comments = listOf(
                Comment(comment1),
                Comment(comment2)
            )
            val post = Post(postId, title, content, createdAt, lastModifiedAt, comments)

            post shouldNotBe null
            post.postId shouldBe postId
            post.title shouldBe title
            post.content shouldBe content
            post.createdAt shouldBe createdAt
            post.lastModifiedAt shouldBe lastModifiedAt
            post.comments shouldBe comments
        }

        test("생성 시각은 수정 시각보다 이후일 수 없다.") {
            shouldThrow<IllegalArgumentException> {
                createPost(LocalDateTime.of(2021, 8, 4, 10, 31), LocalDateTime.of(2021, 8, 4, 10, 30), content = "")
            }
        }

        test("제목은 빈 문자열일 수 없다.") {
            shouldThrow<IllegalArgumentException> {
                createPost(title = "", content = "")
            }
        }

        test("내용은 빈 문자열일 수 없다.") {
            shouldThrow<IllegalArgumentException> {
                createPost(content = "")
            }
        }
    }

    context("편집") {
        test("포스트의 변경 시 수정 시각이 변경된다.") {
            val command = UpdatePostCommand(
                title = "test-title-2",
                content = "test-content-2"
            )
            val post = withConstantNow(LocalDateTime.of(2021, 8, 4, 11, 30)) {
                createPost(title = "test-title-1", content = "test-content-1", lastModifiedAt = LocalDateTime.now())
            }

            withConstantNow(LocalDateTime.of(2021, 8, 4, 11, 31)) {
                post.update(command)

                post.title shouldBe "test-title-2"
                post.content shouldBe "test-content-2"
                post.lastModifiedAt shouldBe LocalDateTime.now()
            }
        }
    }
})

private fun createPost(
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

    return Post(postId, title, content, createdAt, lastModifiedAt, comments)
}

