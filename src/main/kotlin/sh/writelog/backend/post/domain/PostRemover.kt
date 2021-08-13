package sh.writelog.backend.post.domain

import sh.writelog.backend.post.adaptors.outbound.persistence.PostRepository

class PostRemover(
    private val postRepository: PostRepository
) {
    fun remove(postId: PostId) {
        postRepository.remove(postId)
    }
}
