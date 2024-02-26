package com.ttasjwi.board.user.application.port.`in`

import com.ttasjwi.board.user.application.usecase.UserRegisterCommand
import com.ttasjwi.board.user.application.usecase.UserRegisterResult
import com.ttasjwi.board.user.application.usecase.UserRegisterUseCase
import org.springframework.stereotype.Service

@Service
class UserRegisterInputAdapter : UserRegisterUseCase {

    override fun register(command: UserRegisterCommand): UserRegisterResult {
        // TODO: 구현해야함

        return UserRegisterResult(
            id=1L,
            loginId = command.loginId,
            nickname = command.nickname,
            email = command.email,
            role = "USER"
        )
    }
}
