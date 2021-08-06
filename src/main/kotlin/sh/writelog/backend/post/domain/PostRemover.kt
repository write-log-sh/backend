package sh.writelog.backend.post.domain

class PostRemover(
    private val postRepository: PostRepository
) {
    fun remove(postId: PostId) {
        postRepository.remove(postId)
    }
}