package com.ttasjwi.board.system.user.domain.mapper

import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.user.domain.SocialLoginRequest
import com.ttasjwi.board.system.user.domain.dto.SocialLoginCommand

@ApplicationCommandMapper
internal class SocialLoginCommandMapper(
    private val timeManager: TimeManager
){

    fun mapToCommand(request: SocialLoginRequest): SocialLoginCommand {
        val exceptionCollector = ValidationExceptionCollector()
        val state = getState(request.state, exceptionCollector)
        val code = getCode(request.code, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        return SocialLoginCommand(
            state = state!!,
            code = code!!,
            currentTime = timeManager.now()
        )
    }

    private fun getState(state: String?, exceptionCollector: ValidationExceptionCollector): String? {
        if (state == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("state"))
            return null
        }
        return state
    }

    private fun getCode(code: String?, exceptionCollector: ValidationExceptionCollector): String? {
        if (code == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("code"))
            return null
        }
        return code
    }
}
