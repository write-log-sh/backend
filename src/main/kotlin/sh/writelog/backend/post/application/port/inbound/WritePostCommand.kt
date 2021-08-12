package sh.writelog.backend.post.application.port.inbound

data class WritePostCommand(val title: String, val content: String)