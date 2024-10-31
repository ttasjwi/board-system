package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import com.ttasjwi.board.system.core.message.MessageResolver
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableResult
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NicknameAvailableController(
    private val useCase: NicknameAvailableUseCase,
    private val messageResolver: MessageResolver,
) {

    @GetMapping("/api/v1/members/nickname-available")
    fun checkNicknameAvailable(request: NicknameAvailableRequest): ResponseEntity<SuccessResponse<NicknameAvailableResponse>> {
        // 애플리케이션 서비스에 요청 처리를 위임
        val result = useCase.checkNicknameAvailable(request)

        // 처리 결과로부터 응답 메시지 가공
        val response = makeResponse(result)

        // 200 상태코드와 함께 HTTP 응답
        return ResponseEntity.ok(response)
    }

    private fun makeResponse(result: NicknameAvailableResult): SuccessResponse<NicknameAvailableResponse> {
        val code = "NicknameAvailableCheck.Complete"
        return SuccessResponse(
            code = code,
            message = messageResolver.resolveMessage(code),
            description = messageResolver.resolveDescription(code, listOf("$.data.nicknameAvailable")),
            data = NicknameAvailableResponse(
                nicknameAvailable = NicknameAvailableResponse.NicknameAvailable(
                    nickname = result.nickname,
                    isAvailable = result.isAvailable,
                    reasonCode = result.reasonCode,
                    message = messageResolver.resolveMessage(result.reasonCode),
                    description = messageResolver.resolveDescription(result.reasonCode),
                )
            )
        )
    }
}

data class NicknameAvailableResponse(
    val nicknameAvailable: NicknameAvailable
) {

    data class NicknameAvailable(
        val nickname: String,
        val isAvailable: Boolean,
        val reasonCode: String,
        val message: String,
        val description: String
    )
}
