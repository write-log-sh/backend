package sh.writelog.backend.post.ports

import io.kotest.core.spec.style.FunSpec
import io.mockk.mockk
import io.mockk.verify
import sh.writelog.backend.post.domain.Post
import sh.writelog.backend.post.domain.PostRepository

internal class WritePostUseCaseTest : FunSpec({
    lateinit var repository: PostRepository
    lateinit var uut: WritePostUseCase

    beforeTest {
        repository = mockk(relaxed = true)
        uut = WritePostUseCase(repository)
    }

    context("실행") {
        test("포스트를 생성한다.") {
            val title = "test-title"
            val content = "test-content"

            val post = Post.createNew(title, content)
            uut.execute(post)

            verify(exactly = 1) {
                repository.save(any())
            }
        }
    }
})
