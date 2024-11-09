package com.ttasjwi.board.system.auth.application.service

import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.auth.application.usecase.LoginResult
import com.ttasjwi.board.system.auth.application.usecase.LoginUseCase
import com.ttasjwi.board.system.core.annotation.component.ApplicationService

@ApplicationService
class LoginApplicationService : LoginUseCase {

    override fun login(request: LoginRequest): LoginResult {
        TODO("Not yet implemented")
    }
}
