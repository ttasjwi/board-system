package com.ttasjwi.board.system.auth.application.mapper

import com.ttasjwi.board.system.auth.application.dto.LoginCommand
import com.ttasjwi.board.system.auth.application.exception.LoginFailureException
import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.core.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.core.exception.NullArgumentException
import com.ttasjwi.board.system.core.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.core.time.TimeManager
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.RawPassword
import com.ttasjwi.board.system.member.domain.service.EmailCreator
import com.ttasjwi.board.system.member.domain.service.PasswordManager

@ApplicationCommandMapper
internal class LoginCommandMapper(
    private val emailCreator: EmailCreator,
    private val passwordManager: PasswordManager,
    private val timeManager: TimeManager,
) {

    companion object {
        private val log = getLogger(LoginCommandMapper::class.java)
    }


    fun mapToCommand(request: LoginRequest): LoginCommand {
        log.info{ "로그인 요청 필드의 유효성을 확인합니다." }
        checkNullField(request)

        val email = getEmail(request)
        val password = getPassword(request)

        log.info{ "로그인 요청 필드는 유효합니다." }

        return LoginCommand(
            email = email,
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

    private fun getEmail(request: LoginRequest): Email {
        return emailCreator.create(request.email!!)
            .getOrElse {
                val ex = LoginFailureException("로그인 실패 - 이메일 포맷이 유효하지 않음")
                log.warn(ex)
                throw ex
            }
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
