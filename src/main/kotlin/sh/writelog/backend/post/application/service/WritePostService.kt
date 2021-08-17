package sh.writelog.backend.post.application.service

import org.springframework.stereotype.Service
import sh.writelog.backend.post.application.port.inbound.WritePostCommand
import sh.writelog.backend.post.application.port.inbound.WritePostUseCase
import sh.writelog.backend.post.application.port.outbound.SavePostPort
import sh.writelog.backend.post.domain.AuthorId
import sh.writelog.backend.post.domain.Post

@Service
class WritePostService(
    private val savePostPort: SavePostPort
) : WritePostUseCase {
    override fun execute(command: WritePostCommand) {
        val post = Post.createNew(
            authorId = AuthorId(command.authorId),
            title = command.title,
            content = command.content
        )
        savePostPort.save(post)
    }
}
