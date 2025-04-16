package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.user.domain.dto.RegisterMemberCommand
import com.ttasjwi.board.system.user.domain.exception.DuplicateUserEmailException
import com.ttasjwi.board.system.user.domain.exception.DuplicateUserNicknameException
import com.ttasjwi.board.system.user.domain.exception.DuplicateUserUsernameException
import com.ttasjwi.board.system.user.domain.exception.EmailVerificationNotFoundException
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.port.EmailVerificationPersistencePort
import com.ttasjwi.board.system.user.domain.port.MemberPersistencePort
import com.ttasjwi.board.system.user.domain.service.MemberCreator
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class RegisterMemberProcessor(
    private val memberPersistencePort: MemberPersistencePort,
    private val emailVerificationPersistencePort: EmailVerificationPersistencePort,
    private val memberCreator: MemberCreator,
) {

    @Transactional
    fun register(command: RegisterMemberCommand): User {
        checkDuplicate(command)
        checkEmailVerificationAndRemove(command)

        val member = createMember(command)
        memberPersistencePort.save(member)
        return member
    }

    private fun checkDuplicate(command: RegisterMemberCommand) {
        if (memberPersistencePort.existsByEmail(command.email)) {
            throw DuplicateUserEmailException(command.email)
        }
        if (memberPersistencePort.existsByUsername(command.username)) {
            throw DuplicateUserUsernameException(command.username)
        }
        if (memberPersistencePort.existsByNickname(command.nickname)) {
            throw DuplicateUserNicknameException(command.nickname)
        }
    }

    private fun checkEmailVerificationAndRemove(command: RegisterMemberCommand) {
        val emailVerification = emailVerificationPersistencePort.findByEmailOrNull(command.email)
            ?: throw EmailVerificationNotFoundException(command.email)

        // 이메일이 인증됐는지, 그리고 인증이 현재 유효한 지 확인
        emailVerification.throwIfNotVerifiedOrCurrentlyNotValid(command.currentTime)

        // 더 이상 이메일 인증이 필요 없으므로 말소
        emailVerificationPersistencePort.remove(emailVerification.email)
    }

    private fun createMember(command: RegisterMemberCommand): User {
        return memberCreator.create(
            email = command.email,
            rawPassword = command.rawPassword,
            username = command.username,
            nickname = command.nickname,
            currentTime = command.currentTime,
        )
    }
}
