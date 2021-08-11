package sh.writelog.backend.post.ports

import sh.writelog.backend.post.domain.Post
import sh.writelog.backend.post.domain.PostRepository

class WritePostUseCase(private val repository: PostRepository) {
    fun execute(post: Post) {
        repository.save(post)
    }
}