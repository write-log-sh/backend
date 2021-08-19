package sh.writelog.backend.post.adaptors.outbound.remote

import org.springframework.stereotype.Repository
import sh.writelog.backend.post.domain.Author
import sh.writelog.backend.post.domain.AuthorId

@Repository
class FakeAuthorRepository: AuthorRepository {
    override fun loadById(id: AuthorId): Author? {
        return Author(
            id = id,
            name = "author-name"
        )
    }
}