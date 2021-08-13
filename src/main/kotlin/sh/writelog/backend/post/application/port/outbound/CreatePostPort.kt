package sh.writelog.backend.post.application.port.outbound

import sh.writelog.backend.post.domain.Post

interface CreatePostPort {
    fun create(post: Post)
}
