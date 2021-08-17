package sh.writelog.backend.post.application.port.outbound

import sh.writelog.backend.post.domain.Post
import sh.writelog.backend.post.domain.PostId

interface LoadPostPort {
    fun loadById(id: PostId): Post?
}
