package sh.writelog.backend.post.adaptors.outbound.persistence

import sh.writelog.backend.post.application.port.outbound.CreatePostPort
import sh.writelog.backend.post.domain.Post
import sh.writelog.backend.shared.PersistenceAdapter

@PersistenceAdapter
class PostPersistenceAdapter(
    private val repository: PostRepository
) : CreatePostPort {
    override fun create(post: Post) {
        repository.save(post)
    }
}
