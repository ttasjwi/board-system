package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.member.domain.mapper.RegisterMemberCommandMapper
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.processor.RegisterMemberProcessor

@UseCase
internal class RegisterMemberUseCaseImpl(
    private val commandMapper: RegisterMemberCommandMapper,
    private val processor: RegisterMemberProcessor,
) : RegisterMemberUseCase {

    override fun register(request: RegisterMemberRequest): RegisterMemberResponse {
        val command = commandMapper.mapToCommand(request)
        val member = processor.register(command)
        return makeResponse(member)
    }

    private fun makeResponse(member: Member): RegisterMemberResponse {
        return RegisterMemberResponse(
            memberId = member.memberId.toString(),
            email = member.email,
            username = member.username,
            nickname = member.nickname,
            role = member.role.name,
            registeredAt = member.registeredAt.toZonedDateTime(),
        )
    }
}
