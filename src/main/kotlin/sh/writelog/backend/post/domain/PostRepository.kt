package sh.writelog.backend.post.domain

interface PostRepository {
    fun findByPostId(postId: PostId): Post?

    fun remove(postId: PostId)

    fun save(post: Post)
}
