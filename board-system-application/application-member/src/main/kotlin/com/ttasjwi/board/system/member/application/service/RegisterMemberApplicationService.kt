package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.core.application.TransactionRunner
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.application.mapper.RegisterMemberCommandMapper
import com.ttasjwi.board.system.member.application.processor.RegisterMemberProcessor
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberResult
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberUseCase
import com.ttasjwi.board.system.member.domain.event.MemberRegisteredEvent

@ApplicationService
internal class RegisterMemberApplicationService(
    private val commandMapper: RegisterMemberCommandMapper,
    private val processor: RegisterMemberProcessor,
    private val transactionRunner: TransactionRunner
) : RegisterMemberUseCase {

    companion object {
        private val log = getLogger(RegisterMemberApplicationService::class.java)
    }


    override fun register(request: RegisterMemberRequest): RegisterMemberResult {
        log.info { "회원 가입을 시작합니다." }

        // 유효성 검사를 거쳐서 명령으로 변환
        val command = commandMapper.mapToCommand(request)

        // 프로세서에 위임
        val event = transactionRunner.run {
            processor.register(command)
        }

        log.info { "회원가입 됨(id=${event.data.memberId},email = ${event.data.email})" }

        // 처리 결과로 가공, 반환
        return makeResult(event)
    }

    private fun makeResult(event: MemberRegisteredEvent): RegisterMemberResult {
        return RegisterMemberResult(
            memberId = event.data.memberId,
            email = event.data.email,
            username = event.data.username,
            nickname = event.data.nickname,
            role = event.data.roleName,
            registeredAt = event.data.registeredAt,
        )
    }

}
