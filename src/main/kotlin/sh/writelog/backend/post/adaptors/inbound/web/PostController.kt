package sh.writelog.backend.post.adaptors.inbound.web

import java.time.LocalDateTime
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sh.writelog.backend.post.application.port.inbound.CommentQueryResult
import sh.writelog.backend.post.application.port.inbound.ModifyPostCommand
import sh.writelog.backend.post.application.port.inbound.ModifyPostUseCase
import sh.writelog.backend.post.application.port.inbound.ReadPostQuery
import sh.writelog.backend.post.application.port.inbound.ReadPostUseCase
import sh.writelog.backend.post.application.port.inbound.WritePostCommand
import sh.writelog.backend.post.application.port.inbound.WritePostUseCase

@RestController
@RequestMapping("/posts")
class PostController(
    private val writePostUseCase: WritePostUseCase,
    private val modifyPostUseCase: ModifyPostUseCase,
    private val readPostUseCase: ReadPostUseCase,
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

    @GetMapping("/{postId}")
    fun readPost(@PathVariable postId: String): ReadPostViewModel {
        val query = ReadPostQuery(postId)
        val result = readPostUseCase.execute(query)
        return ReadPostViewModel(
            authorName = result.authorName,
            title = result.title,
            content = result.content,
            createdAt = result.createdAt,
            lastModifiedAt = result.lastModifiedAt,
            comments = result.comments
        )
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

data class ReadPostViewModel(
    val authorName: String,
    var title: String,
    var content: String,
    val createdAt: LocalDateTime,
    var lastModifiedAt: LocalDateTime,
    val comments: List<CommentQueryResult>
)
