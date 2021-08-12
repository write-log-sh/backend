
package sh.writelog.backend.post.adaptors.inbound.web

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import sh.writelog.backend.post.application.port.inbound.WritePostCommand
import sh.writelog.backend.post.application.port.inbound.WritePostUseCase

@RestController("/posts")
class PostController(
    private val writePostUseCase: WritePostUseCase
) {
    @PostMapping
    fun writePost(@RequestBody body: WritePostBody) {
        val command = WritePostCommand(body.title, body.content)
        writePostUseCase.execute(command)
    }
}

data class WritePostBody(
    val title: String,
    val content: String
)
