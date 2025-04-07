package com.ttasjwi.board.system.application.member.service

import com.ttasjwi.board.system.application.member.mapper.EmailAvailableQueryMapper
import com.ttasjwi.board.system.application.member.processor.EmailAvailableProcessor
import com.ttasjwi.board.system.application.member.usecase.EmailAvailableRequest
import com.ttasjwi.board.system.application.member.usecase.EmailAvailableResponse
import com.ttasjwi.board.system.application.member.usecase.EmailAvailableUseCase
import com.ttasjwi.board.system.global.annotation.ApplicationService
import com.ttasjwi.board.system.global.logging.getLogger

@ApplicationService
internal class EmailAvailableApplicationService(
    private val queryMapper: EmailAvailableQueryMapper,
    private val processor: EmailAvailableProcessor,
) : EmailAvailableUseCase {

    companion object {
        private val log = getLogger(EmailAvailableApplicationService::class.java)
    }

    override fun checkEmailAvailable(request: EmailAvailableRequest): EmailAvailableResponse {
        log.info { "이메일이 우리 서비스에서 사용 가능한 지 확인합니다." }

        // 유효성 검사를 거쳐서 '질의'로 변환
        val query = queryMapper.mapToQuery(request)

        // 프로세서에 질의 처리 위임
        val response = processor.checkEmailAvailable(query)

        log.info { "이메일 사용가능여부 확인을 마쳤습니다." }
        return response
    }
}
