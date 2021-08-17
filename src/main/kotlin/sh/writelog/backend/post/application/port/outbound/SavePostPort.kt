package sh.writelog.backend.post.application.port.outbound

import sh.writelog.backend.post.domain.Post

interface SavePostPort {
    fun save(post: Post)
}
