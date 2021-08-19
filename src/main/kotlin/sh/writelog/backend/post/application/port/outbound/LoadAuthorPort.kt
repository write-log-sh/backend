package sh.writelog.backend.post.application.port.outbound

import sh.writelog.backend.post.domain.Author
import sh.writelog.backend.post.domain.AuthorId

interface LoadAuthorPort {
    fun loadById(id: AuthorId): Author?
}
