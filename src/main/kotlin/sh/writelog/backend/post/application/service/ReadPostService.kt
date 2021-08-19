package sh.writelog.backend.post.application.service

import org.springframework.stereotype.Service
import sh.writelog.backend.post.application.port.inbound.CommentQueryResult
import sh.writelog.backend.post.application.port.inbound.PostQueryResult
import sh.writelog.backend.post.application.port.inbound.ReadPostException
import sh.writelog.backend.post.application.port.inbound.ReadPostQuery
import sh.writelog.backend.post.application.port.inbound.ReadPostUseCase
import sh.writelog.backend.post.application.port.outbound.LoadAuthorPort
import sh.writelog.backend.post.application.port.outbound.LoadPostPort
import sh.writelog.backend.post.domain.Post
import sh.writelog.backend.post.domain.PostId

@Service
class ReadPostService(
    private val loadPostPort: LoadPostPort,
    private val loadAuthorPort: LoadAuthorPort
) : ReadPostUseCase {
    override fun execute(query: ReadPostQuery): PostQueryResult {
        val post = findPost(query)
        val author = findAuthor(post)
        return PostQueryResult(
            authorName = author.name,
            title = post.title,
            content = post.content,
            createdAt = post.createdAt,
            lastModifiedAt = post.lastModifiedAt,
            comments = post.comments.map {
                CommentQueryResult(
                    comment = it.comment
                )
            }
        )
    }

    private fun findAuthor(post: Post) =
        loadAuthorPort.loadById(post.authorId) ?: throw ReadPostException.AuthorNotFound

    private fun findPost(query: ReadPostQuery) =
        loadPostPort.loadById(PostId(query.postId)) ?: throw ReadPostException.PostNotFound
}