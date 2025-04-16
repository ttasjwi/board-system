package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.user.domain.dto.RegisterUserCommand
import com.ttasjwi.board.system.user.domain.exception.DuplicateUserEmailException
import com.ttasjwi.board.system.user.domain.exception.DuplicateUserNicknameException
import com.ttasjwi.board.system.user.domain.exception.DuplicateUserUsernameException
import com.ttasjwi.board.system.user.domain.exception.EmailVerificationNotFoundException
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.port.EmailVerificationPersistencePort
import com.ttasjwi.board.system.user.domain.port.UserPersistencePort
import com.ttasjwi.board.system.user.domain.service.UserCreator
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class RegisterUserProcessor(
    private val userPersistencePort: UserPersistencePort,
    private val emailVerificationPersistencePort: EmailVerificationPersistencePort,
    private val userCreator: UserCreator,
) {

    @Transactional
    fun register(command: RegisterUserCommand): User {
        checkDuplicate(command)
        checkEmailVerificationAndRemove(command)

        val user = createUser(command)
        userPersistencePort.save(user)
        return user
    }

    private fun checkDuplicate(command: RegisterUserCommand) {
        if (userPersistencePort.existsByEmail(command.email)) {
            throw DuplicateUserEmailException(command.email)
        }
        if (userPersistencePort.existsByUsername(command.username)) {
            throw DuplicateUserUsernameException(command.username)
        }
        if (userPersistencePort.existsByNickname(command.nickname)) {
            throw DuplicateUserNicknameException(command.nickname)
        }
    }

    private fun checkEmailVerificationAndRemove(command: RegisterUserCommand) {
        val emailVerification = emailVerificationPersistencePort.findByEmailOrNull(command.email)
            ?: throw EmailVerificationNotFoundException(command.email)

        // 이메일이 인증됐는지, 그리고 인증이 현재 유효한 지 확인
        emailVerification.throwIfNotVerifiedOrCurrentlyNotValid(command.currentTime)

        // 더 이상 이메일 인증이 필요 없으므로 말소
        emailVerificationPersistencePort.remove(emailVerification.email)
    }

    private fun createUser(command: RegisterUserCommand): User {
        return userCreator.create(
            email = command.email,
            rawPassword = command.rawPassword,
            username = command.username,
            nickname = command.nickname,
            currentTime = command.currentTime,
        )
    }
}
