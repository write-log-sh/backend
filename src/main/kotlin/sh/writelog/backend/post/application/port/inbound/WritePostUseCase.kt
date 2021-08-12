package sh.writelog.backend.post.application.port.inbound

interface WritePostUseCase {
    fun execute(command: WritePostCommand)
}