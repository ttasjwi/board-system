package com.ttasjwi.board.system.application.member.service

import com.ttasjwi.board.system.application.member.mapper.NicknameAvailableQueryMapper
import com.ttasjwi.board.system.application.member.processor.NicknameAvailableProcessor
import com.ttasjwi.board.system.application.member.usecase.NicknameAvailableRequest
import com.ttasjwi.board.system.application.member.usecase.NicknameAvailableResponse
import com.ttasjwi.board.system.application.member.usecase.NicknameAvailableUseCase
import com.ttasjwi.board.system.global.annotation.ApplicationService
import com.ttasjwi.board.system.global.logging.getLogger

@ApplicationService
internal class NicknameAvailableApplicationService(
    private val queryMapper: NicknameAvailableQueryMapper,
    private val processor: NicknameAvailableProcessor,
) : NicknameAvailableUseCase {

    companion object {
        private val log = getLogger(NicknameAvailableApplicationService::class.java)
    }

    override fun checkNicknameAvailable(request: NicknameAvailableRequest): NicknameAvailableResponse {
        log.info { "닉네임 사용가능여부를 확인합니다." }

        // 유효성 검사를 거쳐서 '질의'로 변환
        val query = queryMapper.mapToQuery(request)

        // 프로세서에 위임
        val result = processor.checkNicknameAvailable(query)

        // 처리 결과 반환
        log.info { "닉네임 사용가능여부 확인을 마칩니다." }
        return result
    }
}
