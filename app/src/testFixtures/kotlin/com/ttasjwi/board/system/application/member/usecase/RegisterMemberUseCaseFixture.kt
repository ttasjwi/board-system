package com.ttasjwi.board.system.application.member.usecase

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture

class RegisterMemberUseCaseFixture : RegisterMemberUseCase {

    override fun register(request: RegisterMemberRequest): RegisterMemberResponse {
        return RegisterMemberResponse(
            memberId = "1",
            email = request.email!!,
            username = request.username!!,
            nickname = request.nickname!!,
            role = "USER",
            registeredAt = appDateTimeFixture(minute = 6).toZonedDateTime()
        )
    }
}
