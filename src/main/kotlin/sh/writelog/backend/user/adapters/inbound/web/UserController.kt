package sh.writelog.backend.user.adapters.inbound.web

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sh.writelog.backend.user.application.port.inbound.JoinCommand
import sh.writelog.backend.user.application.port.inbound.JoinUseCase

@RestController
@RequestMapping("/users")
class UserController(private val joinUseCase: JoinUseCase) {
    @PostMapping
    fun join(@RequestBody body: UserBody) {
        joinUseCase.execute(JoinCommand(
            email = body.email,
            nickname = body.nickname,
            profileImageUrl = body.profileImageUrl,
            bio = body.bio
        ))
    }
}
