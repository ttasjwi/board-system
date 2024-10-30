package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.core.application.TransactionRunner
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.application.mapper.EmailAvailableQueryMapper
import com.ttasjwi.board.system.member.application.processor.EmailAvailableProcessor
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableResult
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableUseCase

@ApplicationService
internal class EmailAvailableApplicationService(
    private val queryMapper: EmailAvailableQueryMapper,
    private val processor: EmailAvailableProcessor,
    private val transactionRunner: TransactionRunner,
) : EmailAvailableUseCase {

    companion object {
        private val log = getLogger(EmailAvailableApplicationService::class.java)
    }

    override fun checkEmailAvailable(request: EmailAvailableRequest): EmailAvailableResult {
        log.info{ "이메일이 우리 서비스에서 사용 가능한 지 확인합니다." }

        // 유효성 검사를 거쳐서 '질의'로 변환
        val query = queryMapper.mapToQuery(request)

        // [트랜잭션] 프로세서에 질의 처리 위임
        val result = transactionRunner.runReadOnly { processor.checkEmailAvailable(query) }

        // 처리 결과 반환
        log.info { "이메일 사용가능여부 확인을 마쳤습니다." }
        return result
    }
}
