package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.member.application.mapper.UsernameAvailableQueryMapper
import com.ttasjwi.board.system.member.application.processor.UsernameAvailableProcessor
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableResponse
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableUseCase
import org.springframework.stereotype.Service

@Service
internal class UsernameAvailableApplicationService(
    private val queryMapper: UsernameAvailableQueryMapper,
    private val processor: UsernameAvailableProcessor,
) : UsernameAvailableUseCase {

    companion object {
        private val log = getLogger(UsernameAvailableApplicationService::class.java)
    }

    override fun checkUsernameAvailable(request: UsernameAvailableRequest): UsernameAvailableResponse {
        log.info { "사용자 아이디(username) 사용가능여부를 확인합니다." }

        // 유효성 검사를 거쳐서 '질의'로 변환
        val query = queryMapper.mapToQuery(request)

        // 프로세서에 위임
        val response = processor.checkUsernameAvailable(query)

        log.info { "사용자 아이디(username) 사용가능여부 확인을 마칩니다." }
        return response
    }
}
