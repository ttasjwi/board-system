package com.ttasjwi.board.system.member.application.mapper

import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.member.application.dto.RegisterMemberCommand
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.model.RawPassword
import com.ttasjwi.board.system.member.domain.model.Username
import com.ttasjwi.board.system.member.domain.service.EmailCreator
import com.ttasjwi.board.system.member.domain.service.NicknameCreator
import com.ttasjwi.board.system.member.domain.service.PasswordManager
import com.ttasjwi.board.system.member.domain.service.UsernameCreator
import org.springframework.stereotype.Component

@Component
internal class RegisterMemberCommandMapper(
    private val emailCreator: EmailCreator,
    private val passwordManager: PasswordManager,
    private val usernameCreator: UsernameCreator,
    private val nicknameCreator: NicknameCreator,
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

    private fun getEmail(email: String?, exceptionCollector: ValidationExceptionCollector): Email? {
        if (email == null) {
            log.warn { "이메일이 누락됐습니다." }
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("email"))
            return null
        }
        return emailCreator.create(email)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getRawPassword(password: String?, exceptionCollector: ValidationExceptionCollector): RawPassword? {
        if (password == null) {
            log.warn { "패스워드가 누락됐습니다." }
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("password"))
            return null
        }
        return passwordManager.createRawPassword(password)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getUsername(username: String?, exceptionCollector: ValidationExceptionCollector): Username? {
        if (username == null) {
            log.warn { "사용자 아이디(username)가 누락됐습니다." }
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("username"))
            return null
        }
        return usernameCreator.create(username)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getNickname(nickname: String?, exceptionCollector: ValidationExceptionCollector): Nickname? {
        if (nickname == null) {
            log.warn { "닉네임이 누락됐습니다." }
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("nickname"))
            return null
        }
        return nicknameCreator.create(nickname)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }
}
