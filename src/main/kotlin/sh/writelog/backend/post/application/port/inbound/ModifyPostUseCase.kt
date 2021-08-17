package sh.writelog.backend.post.application.port.inbound

import kotlin.jvm.Throws

interface ModifyPostUseCase {
    @Throws(ModifyPostException.UnableToModifyPost::class)
    fun execute(command: ModifyPostCommand)
}

data class ModifyPostCommand(
    val postId: String,
    val authorId: String,
    val title: String,
    val content: String,
)

sealed class ModifyPostException(message: String) : RuntimeException(message) {
    object UnableToModifyPost: ModifyPostException(
        "포스트를 수정할 수 없습니다."
    )

    object PostNotFound: ModifyPostException(
        "포스트를 수정할 수 없습니다."
    )
}
