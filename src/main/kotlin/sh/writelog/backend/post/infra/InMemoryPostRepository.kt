package sh.writelog.backend.post.infra

import sh.writelog.backend.post.domain.Post
import sh.writelog.backend.post.domain.PostId
import sh.writelog.backend.post.domain.PostRepository

class InMemoryPostRepository : PostRepository {
    private val posts = HashMap<PostId, Post>()

    override fun findByPostId(postId: PostId): Post? {
        return posts[postId]
    }

    override fun remove(postId: PostId) {
        posts.remove(postId)
    }

    override fun save(post: Post) {
        posts[post.postId] = post
    }
}
