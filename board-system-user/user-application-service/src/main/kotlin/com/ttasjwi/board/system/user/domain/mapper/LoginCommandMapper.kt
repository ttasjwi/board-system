package com.ttasjwi.board.system.user.domain.mapper

import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.logger.getLogger
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.user.domain.LoginRequest
import com.ttasjwi.board.system.user.domain.dto.LoginCommand

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
