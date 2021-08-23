package sh.writelog.backend.post.domain

import java.util.UUID

data class PostId(
  val id: String = UUID.randomUUID().toString(),
)
