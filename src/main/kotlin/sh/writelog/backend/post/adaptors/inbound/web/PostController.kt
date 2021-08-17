
package sh.writelog.backend.post.adaptors.inbound.web

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import sh.writelog.backend.post.application.port.inbound.ModifyPostCommand
import sh.writelog.backend.post.application.port.inbound.ModifyPostUseCase
import sh.writelog.backend.post.application.port.inbound.WritePostCommand
import sh.writelog.backend.post.application.port.inbound.WritePostUseCase

@RestController("/posts")
class PostController(
    private val writePostUseCase: WritePostUseCase,
    private val modifyPostUseCase: ModifyPostUseCase
) {
    @PostMapping
    fun writePost(@RequestBody body: WritePostBody) {
        val command = WritePostCommand(body.authorId, body.title, body.content)
        writePostUseCase.execute(command)
    }

    @PutMapping
    fun modifyPost(@RequestBody body: ModifyPostBody) {
        val command = ModifyPostCommand(body.postId, body.authorId, body.title, body.content)
        modifyPostUseCase.execute(command)
    }
}

data class WritePostBody(
    val authorId: String,
    val title: String,
    val content: String
)

data class ModifyPostBody(
    val postId: String,
    val authorId: String,
    val title: String,
    val content: String
)
