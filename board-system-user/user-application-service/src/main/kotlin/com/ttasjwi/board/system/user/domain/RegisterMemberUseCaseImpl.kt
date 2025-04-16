package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.user.domain.mapper.RegisterMemberCommandMapper
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.processor.RegisterMemberProcessor

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

    private fun makeResponse(user: User): RegisterMemberResponse {
        return RegisterMemberResponse(
            memberId = user.userId.toString(),
            email = user.email,
            username = user.username,
            nickname = user.nickname,
            role = user.role.name,
            registeredAt = user.registeredAt.toZonedDateTime(),
        )
    }
}
