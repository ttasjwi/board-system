package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.user.domain.mapper.NicknameAvailableQueryMapper
import com.ttasjwi.board.system.user.domain.processor.NicknameAvailableProcessor

@UseCase
internal class NicknameAvailableUseCaseImpl(
    private val queryMapper: NicknameAvailableQueryMapper,
    private val processor: NicknameAvailableProcessor,
) : NicknameAvailableUseCase {

    override fun checkNicknameAvailable(request: NicknameAvailableRequest): NicknameAvailableResponse {
        val query = queryMapper.mapToQuery(request)
        return processor.checkNicknameAvailable(query)
    }
}
