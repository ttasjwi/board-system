package com.ttasjwi.board.system.application.member.service

import com.ttasjwi.board.system.application.member.mapper.RegisterMemberCommandMapper
import com.ttasjwi.board.system.application.member.processor.RegisterMemberProcessor
import com.ttasjwi.board.system.application.member.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.application.member.usecase.RegisterMemberResponse
import com.ttasjwi.board.system.application.member.usecase.RegisterMemberUseCase
import com.ttasjwi.board.system.global.annotation.ApplicationService
import com.ttasjwi.board.system.global.logging.getLogger
import com.ttasjwi.board.system.member.domain.event.MemberRegisteredEvent

@ApplicationService
internal class RegisterMemberApplicationService(
    private val commandMapper: RegisterMemberCommandMapper,
    private val processor: RegisterMemberProcessor,
) : RegisterMemberUseCase {

    companion object {
        private val log = getLogger(RegisterMemberApplicationService::class.java)
    }


    override fun register(request: RegisterMemberRequest): RegisterMemberResponse {
        log.info { "회원 가입을 시작합니다." }

        // 유효성 검사를 거쳐서 명령으로 변환
        val command = commandMapper.mapToCommand(request)

        // 프로세서에 위임
        val event = processor.register(command)

        log.info { "회원가입 됨(id=${event.data.memberId},email = ${event.data.email})" }

        // 응답 가공, 반환
        return makeResponse(event)
    }

    private fun makeResponse(event: MemberRegisteredEvent): RegisterMemberResponse {
        return RegisterMemberResponse(
            memberId = event.data.memberId.toString(),
            email = event.data.email,
            username = event.data.username,
            nickname = event.data.nickname,
            role = event.data.roleName,
            registeredAt = event.data.registeredAt.toZonedDateTime(),
        )
    }
}
