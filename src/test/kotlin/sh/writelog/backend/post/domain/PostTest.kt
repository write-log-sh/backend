package sh.writelog.backend.post.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.time.withConstantNow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDateTime

internal class PostTest: FunSpec({
    context("create") {
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
            val authorId = AuthorId("test-author-id")
            val post = Post.create(
                authorId = authorId,
                postId = postId,
                title = title,
                content = content,
                createdAt = createdAt,
                lastModifiedAt = lastModifiedAt,
                comments = comments
            )

            post shouldNotBe null
            post.authorId shouldBe authorId
            post.postId shouldBe postId
            post.title shouldBe title
            post.content shouldBe content
            post.createdAt shouldBe createdAt
            post.lastModifiedAt shouldBe lastModifiedAt
            post.comments shouldBe comments
        }

        test("생성 시각은 수정 시각보다 이후일 수 없다.") {
            shouldThrow<IllegalArgumentException> {
                PostFixture.create(
                    LocalDateTime.of(2021, 8, 4, 10, 31),
                    LocalDateTime.of(2021, 8, 4, 10, 30),
                    content = ""
                )
            }
        }

        test("제목은 빈 문자열일 수 없다.") {
            shouldThrow<IllegalArgumentException> {
                PostFixture.create(title = "", content = "")
            }
        }

        test("내용은 빈 문자열일 수 없다.") {
            shouldThrow<IllegalArgumentException> {
                PostFixture.create(content = "")
            }
        }
    }

    context("createNew") {
        test("포스트 ID, 생성 시각, 마지막 수정 시각, 댓글 리스트 없이 포스트를 생성할 수 있다.") {
            val title = "test-title"
            val content = "test-content"
            val now = LocalDateTime.of(2021, 8, 4, 10, 30)
            val authorId = AuthorId("test-author-id")
            val post = withConstantNow(now) {
                Post.createNew(authorId, title, content)
            }

            post shouldNotBe null
            post.authorId shouldBe authorId
            post.postId shouldNotBe null
            post.title shouldBe title
            post.content shouldBe content
            post.createdAt shouldBe now
            post.lastModifiedAt shouldBe now
            post.comments shouldBe emptyList()
        }
    }

    context("편집") {
        test("포스트의 변경 시 수정 시각이 변경된다.") {
            val command = UpdatePostCommand(
                title = "test-title-2",
                content = "test-content-2"
            )
            val post = withConstantNow(LocalDateTime.of(2021, 8, 4, 11, 30)) {
                PostFixture.create(
                    title = "test-title-1",
                    content = "test-content-1",
                    lastModifiedAt = LocalDateTime.now()
                )
            }

            withConstantNow(LocalDateTime.of(2021, 8, 4, 11, 31)) {
                post.update(command)

                post.title shouldBe "test-title-2"
                post.content shouldBe "test-content-2"
                post.lastModifiedAt shouldBe LocalDateTime.now()
            }
        }

        test("포스트 변경 시 제목은 빈 문자열일 수 없다.") {
            val post = PostFixture.create(
                title = "post title",
                content = "content"
            )
            val command = UpdatePostCommand(
                title = ""
            )

            shouldThrow<IllegalArgumentException> {
                post.update(command)
            }
        }

        test("포스트 변경 시 내용은 빈 문자열일 수 없다.") {
            val post = PostFixture.create(
                title = "post title",
                content = "content"
            )
            val command = UpdatePostCommand(
                title = "title",
                content = ""
            )

            shouldThrow<IllegalArgumentException> {
                post.update(command)
            }
        }
    }
})

