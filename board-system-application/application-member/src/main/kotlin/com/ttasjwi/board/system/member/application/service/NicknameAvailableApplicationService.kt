package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.core.application.TransactionRunner
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.application.mapper.NicknameAvailableQueryMapper
import com.ttasjwi.board.system.member.application.processor.NicknameAvailableProcessor
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableResult
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableUseCase

@ApplicationService
internal class NicknameAvailableApplicationService(
    private val queryMapper: NicknameAvailableQueryMapper,
    private val processor: NicknameAvailableProcessor,
    private val transactionRunner: TransactionRunner,
) : NicknameAvailableUseCase {

    companion object {
        private val log = getLogger(NicknameAvailableApplicationService::class.java)
    }

    override fun checkNicknameAvailable(request: NicknameAvailableRequest): NicknameAvailableResult {
        log.info { "닉네임 사용가능여부를 확인합니다." }

        // 유효성 검사를 거쳐서 '질의'로 변환
        val query = queryMapper.mapToQuery(request)

        // 프로세서에 위임
        val result = transactionRunner.runReadOnly {
            processor.checkNicknameAvailable(query)
        }

        // 처리 결과 반환
        log.info { "닉네임 사용가능여부 확인을 마칩니다." }
        return result
    }
}
