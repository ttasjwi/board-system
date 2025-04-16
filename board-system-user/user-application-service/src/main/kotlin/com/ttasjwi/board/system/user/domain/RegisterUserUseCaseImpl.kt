package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.user.domain.mapper.RegisterUserCommandMapper
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.processor.RegisterUserProcessor

@UseCase
internal class RegisterUserUseCaseImpl(
    private val commandMapper: RegisterUserCommandMapper,
    private val processor: RegisterUserProcessor,
) : RegisterUserUseCase {

    override fun register(request: RegisterUserRequest): RegisterUserResponse {
        val command = commandMapper.mapToCommand(request)
        val user = processor.register(command)
        return makeResponse(user)
    }

    private fun makeResponse(user: User): RegisterUserResponse {
        return RegisterUserResponse(
            userId = user.userId.toString(),
            email = user.email,
            username = user.username,
            nickname = user.nickname,
            role = user.role.name,
            registeredAt = user.registeredAt.toZonedDateTime(),
        )
    }
}
