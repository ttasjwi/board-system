package com.ttasjwi.board.system.auth.application.mapper

import com.ttasjwi.board.system.auth.application.dto.LoginCommand
import com.ttasjwi.board.system.auth.application.exception.LoginFailureException
import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.member.domain.model.RawPassword
import com.ttasjwi.board.system.member.domain.service.PasswordManager
import org.springframework.stereotype.Component

@Component
internal class LoginCommandMapper(
    private val passwordManager: PasswordManager,
    private val timeManager: TimeManager,
) {

    companion object {
        private val log = getLogger(LoginCommandMapper::class.java)
    }


    fun mapToCommand(request: LoginRequest): LoginCommand {
        log.info { "로그인 요청 필드의 유효성을 확인합니다." }
        checkNullField(request)

        val password = getPassword(request)

        log.info { "로그인 요청 필드는 유효합니다." }

        return LoginCommand(
            email = request.email!!,
            rawPassword = password,
            currentTime = timeManager.now()
        )
    }

    private fun checkNullField(request: LoginRequest) {
        val exceptionCollector = ValidationExceptionCollector()
        if (request.email == null) {
            NullArgumentException("email")
                .let {
                    log.warn(it)
                    exceptionCollector.addCustomExceptionOrThrow(it)
                }
        }
        if (request.password == null) {
            NullArgumentException("password")
                .let {
                    log.warn(it)
                    exceptionCollector.addCustomExceptionOrThrow(it)
                }
        }
        exceptionCollector.throwIfNotEmpty()
    }

    private fun getPassword(request: LoginRequest): RawPassword {
        return passwordManager.createRawPassword(request.password!!)
            .getOrElse {
                val ex = LoginFailureException("로그인 실패 - 패스워드 포맷이 유효하지 않음")
                log.warn { ex }
                throw ex
            }
    }

}
