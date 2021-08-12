package sh.writelog.backend.post.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import sh.writelog.backend.post.adaptors.outbound.persistence.InMemoryPostRepository

internal class PostRemoverTest: FunSpec({
    val postId = PostId("test-post-id")

    lateinit var postRepository: PostRepository
    lateinit var sut: PostRemover

    beforeTest {
        postRepository = InMemoryPostRepository()
        sut = PostRemover(postRepository)
    }

    context("remove") {
        test("포스트가 존재하지 않는 경우 아무런 일도 일어나지 않는다.") {
            sut.remove(postId)

            postRepository.findByPostId(postId) shouldBe null
        }

        test("포스트가 존재하는 경우 해당 포스트를 삭제한다.") {
            sut.remove(postId)

            postRepository.findByPostId(postId) shouldBe null
        }
    }
})