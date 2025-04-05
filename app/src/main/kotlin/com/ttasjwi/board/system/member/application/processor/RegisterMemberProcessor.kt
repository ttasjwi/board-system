package com.ttasjwi.board.system.member.application.processor

import com.ttasjwi.board.system.global.annotation.ApplicationProcessor
import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.logging.getLogger
import com.ttasjwi.board.system.member.application.dto.RegisterMemberCommand
import com.ttasjwi.board.system.member.application.exception.DuplicateMemberEmailException
import com.ttasjwi.board.system.member.application.exception.DuplicateMemberNicknameException
import com.ttasjwi.board.system.member.application.exception.DuplicateMemberUsernameException
import com.ttasjwi.board.system.member.application.exception.EmailVerificationNotFoundException
import com.ttasjwi.board.system.member.domain.event.MemberRegisteredEvent
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.service.*
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class RegisterMemberProcessor(
    private val memberFinder: MemberFinder,
    private val emailVerificationFinder: EmailVerificationFinder,
    private val emailVerificationHandler: EmailVerificationHandler,
    private val emailVerificationAppender: EmailVerificationAppender,
    private val memberCreator: MemberCreator,
    private val memberAppender: MemberAppender,
    private val memberEventCreator: MemberEventCreator,
) {

    companion object {
        private val log = getLogger(RegisterMemberProcessor::class.java)
    }

    @Transactional
    fun register(command: RegisterMemberCommand): MemberRegisteredEvent {
        checkDuplicate(command)
        checkEmailVerificationAndRemove(command)

        val member = createMember(command)

        val savedMember = memberAppender.save(member)
        log.info { "회원 생성 및 저장됨 (memberId=${savedMember.id})" }

        val event = memberEventCreator.onMemberRegistered(savedMember)
        return event
    }

    private fun checkDuplicate(command: RegisterMemberCommand) {
        log.info { "중복되는 회원이 있는 지 확인합니다. " }
        val ex: CustomException
        if (memberFinder.existsByEmail(command.email)) {
            ex = DuplicateMemberEmailException(command.email)
            log.warn(ex)
            throw ex
        }
        if (memberFinder.existsByUsername(command.username)) {
            ex = DuplicateMemberUsernameException(command.username)
            log.warn(ex)
            throw ex
        }
        if (memberFinder.existsByNickname(command.nickname)) {
            ex = DuplicateMemberNicknameException(command.nickname)
            log.warn(ex)
            throw ex
        }
        log.info { "중복되는 회원이 없습니다." }
    }

    private fun checkEmailVerificationAndRemove(command: RegisterMemberCommand) {
        log.info { "이메일 인증을 조회합니다. (email=${command.email})" }
        val emailVerification = emailVerificationFinder.findByEmailOrNull(command.email)

        if (emailVerification == null) {
            val ex = EmailVerificationNotFoundException(command.email)
            log.warn(ex)
            throw ex
        }
        log.info { "이메일 인증이 존재합니다." }

        // 이메일이 인증됐는지, 그리고 인증이 현재 유효한 지 확인
        emailVerificationHandler.checkVerifiedAndCurrentlyValid(emailVerification, command.currentTime)

        // 더 이상 이메일 인증이 필요 없으므로 말소
        emailVerificationAppender.removeByEmail(emailVerification.email)
    }

    private fun createMember(command: RegisterMemberCommand): Member {
        return memberCreator.create(
            email = command.email,
            password = command.rawPassword,
            username = command.username,
            nickname = command.nickname,
            currentTime = command.currentTime,
        )
    }
}
