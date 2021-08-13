package sh.writelog.backend.post.adaptors.outbound.persistence

import sh.writelog.backend.post.domain.Post
import sh.writelog.backend.post.domain.PostId

interface PostRepository {
    fun findByPostId(postId: PostId): Post?

    fun remove(postId: PostId)

    fun save(post: Post)
}
