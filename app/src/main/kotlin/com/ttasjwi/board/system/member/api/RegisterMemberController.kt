package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.common.api.SuccessResponse
import com.ttasjwi.board.system.common.locale.LocaleManager
import com.ttasjwi.board.system.common.message.MessageResolver
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberResult
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class RegisterMemberController(
    private val useCase: RegisterMemberUseCase,
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    @PostMapping("/api/v1/members")
    fun register(@RequestBody request: RegisterMemberRequest): ResponseEntity<SuccessResponse<RegisterMemberResponse>> {
        // 애플리케이션 서비스에 요청 처리를 위임
        val result = useCase.register(request)

        // 처리 결과로부터 응답 메시지 가공
        val response = makeResponse(result)

        // 201 상태코드와 함께 HTTP 응답
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    private fun makeResponse(result: RegisterMemberResult): SuccessResponse<RegisterMemberResponse> {
        val code = "RegisterMember.Complete"
        val locale = localeManager.getCurrentLocale()
        return SuccessResponse(
            code = code,
            message = messageResolver.resolve("$code.message", locale),
            description = messageResolver.resolve("$code.description", locale),
            data = RegisterMemberResponse(
                registeredMember = RegisterMemberResponse.RegisteredMember(
                    memberId = result.memberId,
                    email = result.email,
                    username = result.username,
                    nickname = result.nickname,
                    role = result.role,
                    registeredAt = result.registeredAt
                )
            )
        )
    }
}

data class RegisterMemberResponse(
    val registeredMember: RegisteredMember
) {

    data class RegisteredMember(
        val memberId: Long,
        val email: String,
        val username: String,
        val nickname: String,
        val role: String,
        val registeredAt: ZonedDateTime,
    )
}
