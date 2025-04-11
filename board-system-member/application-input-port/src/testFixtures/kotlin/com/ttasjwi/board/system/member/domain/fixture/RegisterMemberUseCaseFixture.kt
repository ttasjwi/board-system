package com.ttasjwi.board.system.member.domain.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.RegisterMemberRequest
import com.ttasjwi.board.system.member.domain.RegisterMemberResponse
import com.ttasjwi.board.system.member.domain.RegisterMemberUseCase

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
