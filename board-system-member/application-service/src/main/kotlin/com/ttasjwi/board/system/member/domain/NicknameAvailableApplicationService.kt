package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.common.annotation.component.ApplicationService
import com.ttasjwi.board.system.member.domain.mapper.NicknameAvailableQueryMapper
import com.ttasjwi.board.system.member.domain.processor.NicknameAvailableProcessor

@ApplicationService
internal class NicknameAvailableApplicationService(
    private val queryMapper: NicknameAvailableQueryMapper,
    private val processor: NicknameAvailableProcessor,
) : NicknameAvailableUseCase {

    override fun checkNicknameAvailable(request: NicknameAvailableRequest): NicknameAvailableResponse {
        val query = queryMapper.mapToQuery(request)
        return processor.checkNicknameAvailable(query)
    }
}
