package sh.writelog.backend.post

import java.time.LocalDateTime

class Post(
    val postId: PostId,
    var title: String,
    var content: String,
    val createdAt: LocalDateTime,
    var lastModifiedAt: LocalDateTime,
    val comments: List<Comment>
) {
    init {
        if (createdAt > lastModifiedAt) {
            throw IllegalArgumentException("생성 시각은 수정 시각보다 이후일 수 없습니다.")
        }

        if (title.isEmpty() || content.isEmpty()) {
            throw IllegalArgumentException("제목이나 내용은 빈 문자열일 수 없습니다.")
        }
    }

    fun update(command: UpdatePostCommand) {
        this.title = command.title
        this.content = command.content
        this.lastModifiedAt = LocalDateTime.now()
    }
}
