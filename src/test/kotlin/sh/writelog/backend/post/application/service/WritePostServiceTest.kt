package sh.writelog.backend.post.application.service

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import sh.writelog.backend.post.application.port.inbound.WritePostCommand
import sh.writelog.backend.post.application.port.outbound.CreatePostPort
import sh.writelog.backend.post.domain.Post

@DisplayName("WritePostService 테스트")
internal class WritePostServiceTest : FunSpec({
    lateinit var uut: WritePostService
    lateinit var port: CreatePostPort

    beforeTest {
        port = mockk(relaxed = true)
        uut = WritePostService(port)
    }

    context("포스트 작성") {
        test("PostCommand로 작성을 요청하면 port에 동일한 파라미터가 세팅되어야 한다.") {
            val title = "test-title"
            val content = "test-content"
            val command = WritePostCommand(
                title = title,
                content = content
            )

            uut.execute(command)

            val slot = slot<Post>()
            verify(exactly = 1) {
                port.create(capture(slot))
            }
            slot.captured.title shouldBe title
            slot.captured.content shouldBe content
        }
    }
})
