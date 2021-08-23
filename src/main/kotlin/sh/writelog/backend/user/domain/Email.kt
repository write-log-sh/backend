package sh.writelog.backend.user.domain

// Ref: android.util.Patterns
private val EMAIL_REGEX = Regex(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
)

data class Email(val value: String) {
    init {
        require(value.matches(EMAIL_REGEX)) {
            "이메일은 유효한 형식이어야 합니다."
        }
    }
}