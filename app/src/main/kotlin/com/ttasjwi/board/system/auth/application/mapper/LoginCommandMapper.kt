package com.ttasjwi.board.system.auth.application.mapper

import com.ttasjwi.board.system.auth.application.dto.LoginCommand
import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.global.annotation.ApplicationCommandMapper
import com.ttasjwi.board.system.global.exception.NullArgumentException
import com.ttasjwi.board.system.global.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.global.logging.getLogger
import com.ttasjwi.board.system.global.time.TimeManager

@ApplicationCommandMapper
internal class LoginCommandMapper(
    private val timeManager: TimeManager,
) {

    companion object {
        private val log = getLogger(LoginCommandMapper::class.java)
    }


    fun mapToCommand(request: LoginRequest): LoginCommand {
        log.info { "로그인 요청 필드의 유효성을 확인합니다." }
        checkNullField(request)

        log.info { "로그인 요청 필드는 유효합니다." }

        return LoginCommand(
            email = request.email!!,
            rawPassword = request.password!!,
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
}
