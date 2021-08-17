package sh.writelog.backend.post.application.service

import org.springframework.stereotype.Service
import sh.writelog.backend.post.application.port.inbound.ModifyPostCommand
import sh.writelog.backend.post.application.port.inbound.ModifyPostException
import sh.writelog.backend.post.application.port.inbound.ModifyPostUseCase
import sh.writelog.backend.post.application.port.outbound.LoadPostPort
import sh.writelog.backend.post.application.port.outbound.SavePostPort
import sh.writelog.backend.post.domain.AuthorId
import sh.writelog.backend.post.domain.Post
import sh.writelog.backend.post.domain.PostId
import sh.writelog.backend.post.domain.UpdatePostCommand

@Service
class ModifyPostService(
    private val loadPostPort: LoadPostPort,
    private val savePostPort: SavePostPort,
) : ModifyPostUseCase {
    override fun execute(command: ModifyPostCommand) {
        val post = findPost(command.postId)
        checkAuthorId(post, command)

        post.update(UpdatePostCommand(title = command.title, content = command.content))

        savePostPort.save(post)
    }

    private fun checkAuthorId(post: Post, command: ModifyPostCommand) {
        if (post.authorId != AuthorId(command.authorId)) {
            throw ModifyPostException.UnableToModifyPost
        }
    }

    private fun findPost(postId: String) =
        loadPostPort.loadById(PostId(postId)) ?: throw ModifyPostException.PostNotFound
}
