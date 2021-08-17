package sh.writelog.backend.post.domain

data class UpdatePostCommand(val title: String? = null, val content: String? = null)
