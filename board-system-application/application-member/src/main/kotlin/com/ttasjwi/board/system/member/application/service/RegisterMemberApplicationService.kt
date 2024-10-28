package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberResult
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberUseCase

@ApplicationService
internal class RegisterMemberApplicationService : RegisterMemberUseCase {

    override fun register(request: RegisterMemberRequest): RegisterMemberResult {
        TODO("Not yet implemented")
    }
}
