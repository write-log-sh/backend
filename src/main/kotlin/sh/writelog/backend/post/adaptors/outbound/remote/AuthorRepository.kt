package sh.writelog.backend.post.adaptors.outbound.remote

import sh.writelog.backend.post.domain.Author
import sh.writelog.backend.post.domain.AuthorId

interface AuthorRepository {
    fun loadById(id: AuthorId): Author?
}