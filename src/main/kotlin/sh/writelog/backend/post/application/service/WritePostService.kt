package sh.writelog.backend.post.application.service

import org.springframework.stereotype.Service
import sh.writelog.backend.post.application.port.inbound.WritePostCommand
import sh.writelog.backend.post.application.port.inbound.WritePostUseCase

@Service
class WritePostService: WritePostUseCase {
    override fun execute(command: WritePostCommand) {
        TODO("Not yet implemented")
    }
}
