package sh.writelog.backend.post.application.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import sh.writelog.backend.post.application.port.inbound.CommentQueryResult
import sh.writelog.backend.post.application.port.inbound.PostQueryResult
import sh.writelog.backend.post.application.port.inbound.ReadPostException
import sh.writelog.backend.post.application.port.inbound.ReadPostQuery
import sh.writelog.backend.post.application.port.outbound.LoadAuthorPort
import sh.writelog.backend.post.application.port.outbound.LoadPostPort
import sh.writelog.backend.post.domain.Author
import sh.writelog.backend.post.domain.AuthorId
import sh.writelog.backend.post.domain.Comment
import sh.writelog.backend.post.domain.Post
import sh.writelog.backend.post.domain.PostId

@DisplayName("ReadPostService 테스트")
internal class ReadPostServiceTest: FunSpec({
    lateinit var loadAuthorPort: LoadAuthorPort
    lateinit var loadPostPort: LoadPostPort
    lateinit var uut: ReadPostService

    beforeTest {
        loadPostPort = mockk(relaxed = true)
        loadAuthorPort = mockk(relaxed = true)
        uut = ReadPostService(loadPostPort, loadAuthorPort)
    }

    context("실행") {
        val postId = "test-post-id"
        val authorIdString = "test-author-id"

        test("포스트를 찾을 수 없는 경우 PostNotFound를 throw 한다.") {
            every { loadPostPort.loadById(PostId(postId)) } returns null
            val query = ReadPostQuery(postId = postId)

            shouldThrow<ReadPostException.PostNotFound> {
                uut.execute(query)
            }
        }

        test("작성자를 찾을 수 없는 경우 AuthorNotFound를 throw 한다.") {
            every { loadPostPort.loadById(PostId(postId)) } returns mockk<Post>().apply {
                every { authorId } returns AuthorId(authorIdString)
            }
            every { loadAuthorPort.loadById(AuthorId(authorIdString)) } returns null
            val query = ReadPostQuery(postId = postId)

            shouldThrow<ReadPostException.AuthorNotFound> {
                uut.execute(query)
            }
        }

        test("포스트를 반환한다.") {
            val title = "test-title"
            val content = "test-content"
            val createdAt = LocalDateTime.of(2021, 8, 18, 23, 0)
            val lastModifiedAt = LocalDateTime.of(2021, 8, 18, 23, 0)
            val author = Author(
                id = AuthorId(authorIdString),
                name = "test-author-name"
            )
            val post = Post.create(
                authorId = author.id,
                postId = PostId(postId),
                title = title,
                content = content,
                createdAt = createdAt,
                lastModifiedAt = lastModifiedAt,
                comments = listOf(
                    Comment("test-comment-1"),
                    Comment("test-comment-2")
                )
            )
            every { loadPostPort.loadById(PostId(postId)) } returns post
            every { loadAuthorPort.loadById(AuthorId(authorIdString)) } returns author

            val query = ReadPostQuery(postId = postId)
            val result = uut.execute(query)

            result shouldBe PostQueryResult(
                authorName = author.name,
                title = title,
                content = content,
                createdAt = createdAt,
                lastModifiedAt = lastModifiedAt,
                comments = listOf(
                    CommentQueryResult(
                        comment = "test-comment-1"
                    ),
                    CommentQueryResult(
                        comment = "test-comment-2"
                    )
                )
            )
        }
    }
})
