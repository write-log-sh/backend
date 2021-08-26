package sh.writelog.backend.user.adapters.inbound.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sh.writelog.backend.user.application.port.inbound.JoinCommand
import sh.writelog.backend.user.application.port.inbound.JoinUseCase
import sh.writelog.backend.user.application.port.inbound.ResignCommand
import sh.writelog.backend.user.application.port.inbound.ResignException
import sh.writelog.backend.user.application.port.inbound.ResignUseCase

@RestController
@RequestMapping("/users")
class UserController(
    private val joinUseCase: JoinUseCase,
    private val resignUseCase: ResignUseCase,
) {
    @PostMapping
    fun join(@RequestBody body: UserBody) {
        joinUseCase.execute(JoinCommand(
            email = body.email,
            nickname = body.nickname,
            profileImageUrl = body.profileImageUrl,
            bio = body.bio
        ))
    }

    @DeleteMapping("/{userId}")
    fun resign(@PathVariable userId: String): ResponseEntity<Unit> {
        try {
            resignUseCase.execute(ResignCommand(userId = userId))
        } catch (exception: ResignException) {
            when (exception) {
                ResignException.UserNotFound -> return ResponseEntity<Unit>(HttpStatus.NOT_FOUND)
            }
        }

        return ResponseEntity<Unit>(HttpStatus.OK)
    }
}
