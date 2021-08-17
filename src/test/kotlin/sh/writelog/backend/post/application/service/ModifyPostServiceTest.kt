package sh.writelog.backend.post.application.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import sh.writelog.backend.post.application.port.inbound.ModifyPostCommand
import sh.writelog.backend.post.application.port.inbound.ModifyPostException
import sh.writelog.backend.post.application.port.outbound.LoadPostPort
import sh.writelog.backend.post.application.port.outbound.SavePostPort
import sh.writelog.backend.post.domain.AuthorId
import sh.writelog.backend.post.domain.Post
import sh.writelog.backend.post.domain.PostId
import sh.writelog.backend.post.domain.UpdatePostCommand

@DisplayName("ModifyPostService 테스트")
internal class ModifyPostServiceTest: FunSpec({
    lateinit var loadPostPort: LoadPostPort
    lateinit var savePostPort: SavePostPort
    lateinit var uut: ModifyPostService

    beforeTest {
        loadPostPort = mockk(relaxed = true)
        savePostPort = mockk(relaxed = true)
        uut = ModifyPostService(loadPostPort, savePostPort)
    }

    context("실행") {
        test("포스트가 존재하지 않는다면 PostNotFound 를 throw 한다.") {
            val authorId = "test-author-id"
            val title = "test-title"
            val content = "test-content"
            val postId = "test-post-id"
            val command = ModifyPostCommand(postId, authorId, title, content)
            every { loadPostPort.loadById(PostId(postId)) } returns null

            shouldThrow<ModifyPostException.PostNotFound> {
                uut.execute(command)
            }
        }

        test("작성자가 아니라면 UnableToModifyPost 를 throw 한다.") {
            val postAuthorId = "test-author-id"
            val title = "test-title"
            val content = "test-content"
            val postId = "test-post-id"
            val command = ModifyPostCommand(postId, postAuthorId, title, content)
            val post = mockk<Post>().apply {
                every { authorId } returns AuthorId("other-author-id")
            }
            every { loadPostPort.loadById(PostId(postId)) } returns post

            shouldThrow<ModifyPostException.UnableToModifyPost> {
                uut.execute(command)
            }
        }

        test("포스트를 수정한다.") {
            val postAuthorId = "test-author-id"
            val title = "test-title"
            val content = "test-content"
            val postId = "test-post-id"
            val command = ModifyPostCommand(postId, postAuthorId, title, content)
            val post = mockk<Post>(relaxed = true).apply {
                every { authorId } returns AuthorId(postAuthorId)
            }
            every { loadPostPort.loadById(PostId(postId)) } returns post

            uut.execute(command)

            verify(exactly = 1) {
                post.update(UpdatePostCommand(title = title, content = content))
                savePostPort.save(post)
            }
        }
    }
})
