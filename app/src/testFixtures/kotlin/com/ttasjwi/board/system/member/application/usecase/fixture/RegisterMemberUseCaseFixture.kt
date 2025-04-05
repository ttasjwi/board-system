package com.ttasjwi.board.system.member.application.usecase.fixture

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberResponse
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberUseCase

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
