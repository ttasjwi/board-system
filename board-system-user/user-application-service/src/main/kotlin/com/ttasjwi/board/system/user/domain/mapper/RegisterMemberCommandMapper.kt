package com.ttasjwi.board.system.user.domain.mapper

import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.logger.getLogger
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.user.domain.RegisterMemberRequest
import com.ttasjwi.board.system.user.domain.dto.RegisterMemberCommand
import com.ttasjwi.board.system.user.domain.policy.NicknamePolicy
import com.ttasjwi.board.system.user.domain.policy.PasswordPolicy
import com.ttasjwi.board.system.user.domain.policy.UsernamePolicy
import com.ttasjwi.board.system.user.domain.port.EmailFormatValidatePort

@ApplicationCommandMapper
internal class RegisterMemberCommandMapper(
    private val emailFormatValidatePort: EmailFormatValidatePort,
    private val usernamePolicy: UsernamePolicy,
    private val nicknamePolicy: NicknamePolicy,
    private val passwordPolicy: PasswordPolicy,
    private val timeManager: TimeManager,
) {
    companion object {
        private val log = getLogger(RegisterMemberCommandMapper::class.java)
    }

    fun mapToCommand(request: RegisterMemberRequest): RegisterMemberCommand {
        log.info { "요청 입력값이 유효한 지 확인합니다." }
        val exceptionCollector = ValidationExceptionCollector()

        val email = getEmail(request.email, exceptionCollector)
        val rawPassword = getRawPassword(request.password, exceptionCollector)
        val username = getUsername(request.username, exceptionCollector)
        val nickname = getNickname(request.nickname, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        log.info { "요청 입력값들은 유효합니다." }

        return RegisterMemberCommand(
            email = email!!,
            rawPassword = rawPassword!!,
            username = username!!,
            nickname = nickname!!,
            currentTime = timeManager.now()
        )
    }

    private fun getEmail(email: String?, exceptionCollector: ValidationExceptionCollector): String? {
        if (email == null) {
            log.warn { "이메일이 누락됐습니다." }
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("email"))
            return null
        }
        return emailFormatValidatePort.validate(email)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getRawPassword(password: String?, exceptionCollector: ValidationExceptionCollector): String? {
        if (password == null) {
            log.warn { "패스워드가 누락됐습니다." }
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("password"))
            return null
        }
        return passwordPolicy.validate(password)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getUsername(username: String?, exceptionCollector: ValidationExceptionCollector): String? {
        if (username == null) {
            log.warn { "사용자 아이디(username)가 누락됐습니다." }
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("username"))
            return null
        }
        return usernamePolicy.validate(username)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getNickname(nickname: String?, exceptionCollector: ValidationExceptionCollector): String? {
        if (nickname == null) {
            log.warn { "닉네임이 누락됐습니다." }
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("nickname"))
            return null
        }
        return nicknamePolicy.validate(nickname)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }
}
