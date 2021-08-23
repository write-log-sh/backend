package sh.writelog.backend.user.domain

// 오..
data class Nickname(val value: String) {
    init {
        require(value.isNotBlank()) {
            "닉네임은 빈 값일 수 없습니다."
        }
    }
}