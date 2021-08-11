package sh.writelog.backend.post.adaptors.presentation

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import sh.writelog.backend.post.ports.WritePostUseCase

@RestController("/posts")
class PostController(
    private val writePostUseCase: WritePostUseCase
) {
    @PostMapping
    fun writePost(@RequestBody body: WritePostBody) {
        writePostUseCase.execute()
    }
}

data class WritePostBody(
    val title: String,
    val content: String
)
