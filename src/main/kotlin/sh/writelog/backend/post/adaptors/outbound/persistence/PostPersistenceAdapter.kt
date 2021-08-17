package sh.writelog.backend.post.adaptors.outbound.persistence

import sh.writelog.backend.post.application.port.outbound.LoadPostPort
import sh.writelog.backend.post.application.port.outbound.SavePostPort
import sh.writelog.backend.post.domain.Post
import sh.writelog.backend.post.domain.PostId
import sh.writelog.backend.shared.PersistenceAdapter

@PersistenceAdapter
class PostPersistenceAdapter(
    private val repository: PostRepository
) : SavePostPort, LoadPostPort {
    override fun save(post: Post) {
        repository.save(post)
    }

    override fun loadById(id: PostId): Post? {
        return repository.findByPostId(id)
    }
}
