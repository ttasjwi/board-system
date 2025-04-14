package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.member.domain.mapper.EmailAvailableQueryMapper
import com.ttasjwi.board.system.member.domain.processor.EmailAvailableProcessor

@UseCase
internal class EmailAvailableUseCaseImpl(
    private val queryMapper: EmailAvailableQueryMapper,
    private val processor: EmailAvailableProcessor,
) : EmailAvailableUseCase {

    override fun checkEmailAvailable(request: EmailAvailableRequest): EmailAvailableResponse {
        val query = queryMapper.mapToQuery(request)
        return processor.checkEmailAvailable(query)
    }
}
