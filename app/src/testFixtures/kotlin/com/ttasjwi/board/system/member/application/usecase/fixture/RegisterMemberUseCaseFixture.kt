package com.ttasjwi.board.system.member.application.usecase.fixture

import com.ttasjwi.board.system.common.time.fixture.timeFixture
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberResult
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberUseCase

class RegisterMemberUseCaseFixture : RegisterMemberUseCase {

    override fun register(request: RegisterMemberRequest): RegisterMemberResult {
        return RegisterMemberResult(
            memberId = 1L,
            email = request.email!!,
            username = request.username!!,
            nickname = request.nickname!!,
            role = "USER",
            registeredAt = timeFixture(minute = 6)
        )
    }
}
