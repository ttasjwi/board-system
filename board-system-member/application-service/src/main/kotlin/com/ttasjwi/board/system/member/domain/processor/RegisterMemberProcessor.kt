package com.ttasjwi.board.system.member.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import com.ttasjwi.board.system.member.domain.dto.RegisterMemberCommand
import com.ttasjwi.board.system.member.domain.exception.DuplicateMemberEmailException
import com.ttasjwi.board.system.member.domain.exception.DuplicateMemberNicknameException
import com.ttasjwi.board.system.member.domain.exception.DuplicateMemberUsernameException
import com.ttasjwi.board.system.member.domain.exception.EmailVerificationNotFoundException
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.port.EmailVerificationPersistencePort
import com.ttasjwi.board.system.member.domain.port.MemberPersistencePort
import com.ttasjwi.board.system.member.domain.port.PasswordEncryptionPort
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class RegisterMemberProcessor(
    private val memberPersistencePort: MemberPersistencePort,
    private val passwordEncryptionPort: PasswordEncryptionPort,
    private val emailVerificationPersistencePort: EmailVerificationPersistencePort,
) {

    private val idGenerator: IdGenerator = IdGenerator.create()

    @Transactional
    fun register(command: RegisterMemberCommand): Member {
        checkDuplicate(command)
        checkEmailVerificationAndRemove(command)

        val member = createMember(command)
        memberPersistencePort.save(member)
        return member
    }

    private fun checkDuplicate(command: RegisterMemberCommand) {
        if (memberPersistencePort.existsByEmail(command.email)) {
            throw DuplicateMemberEmailException(command.email)
        }
        if (memberPersistencePort.existsByUsername(command.username)) {
            throw DuplicateMemberUsernameException(command.username)
        }
        if (memberPersistencePort.existsByNickname(command.nickname)) {
            throw DuplicateMemberNicknameException(command.nickname)
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

    private fun createMember(command: RegisterMemberCommand): Member {
        return Member.create(
            memberId = idGenerator.nextId(),
            email = command.email,
            password = passwordEncryptionPort.encode(command.rawPassword),
            username = command.username,
            nickname = command.nickname,
            registeredAt = command.currentTime,
        )
    }
}
