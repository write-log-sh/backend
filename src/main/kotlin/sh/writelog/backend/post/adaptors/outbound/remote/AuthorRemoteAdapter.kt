package sh.writelog.backend.post.adaptors.outbound.remote

import org.springframework.stereotype.Component
import sh.writelog.backend.post.application.port.outbound.LoadAuthorPort
import sh.writelog.backend.post.domain.Author
import sh.writelog.backend.post.domain.AuthorId

@Component
class AuthorRemoteAdapter(
    private val authorRepository: AuthorRepository
): LoadAuthorPort {
    override fun loadById(id: AuthorId): Author? {
        return authorRepository.loadById(id)
    }
}