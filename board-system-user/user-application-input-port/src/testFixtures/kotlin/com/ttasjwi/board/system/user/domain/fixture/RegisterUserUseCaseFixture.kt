package com.ttasjwi.board.system.user.domain.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.RegisterUserRequest
import com.ttasjwi.board.system.user.domain.RegisterUserResponse
import com.ttasjwi.board.system.user.domain.RegisterUserUseCase

class RegisterUserUseCaseFixture : RegisterUserUseCase {

    override fun register(request: RegisterUserRequest): RegisterUserResponse {
        return RegisterUserResponse(
            userId = "1",
            email = request.email!!,
            username = request.username!!,
            nickname = request.nickname!!,
            role = "USER",
            registeredAt = appDateTimeFixture(minute = 6).toZonedDateTime()
        )
    }
}
