package sh.writelog.backend.post.application.service

import org.springframework.stereotype.Service
import sh.writelog.backend.post.application.port.inbound.WritePostCommand
import sh.writelog.backend.post.application.port.inbound.WritePostUseCase
import sh.writelog.backend.post.application.port.outbound.CreatePostPort
import sh.writelog.backend.post.domain.Post

@Service
class WritePostService(
    private val createPostPort: CreatePostPort
) : WritePostUseCase {
    override fun execute(command: WritePostCommand) {
        val post = Post.createNew(title = command.title, content = command.content)
        createPostPort.create(post)
    }
}
