package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.user.domain.mapper.UsernameAvailableQueryMapper
import com.ttasjwi.board.system.user.domain.processor.UsernameAvailableProcessor

@UseCase
internal class UsernameAvailableUseCaseImpl(
    private val queryMapper: UsernameAvailableQueryMapper,
    private val processor: UsernameAvailableProcessor,
) : UsernameAvailableUseCase {

    override fun checkUsernameAvailable(request: UsernameAvailableRequest): UsernameAvailableResponse {
        val query = queryMapper.mapToQuery(request)
        return processor.checkUsernameAvailable(query)
    }
}
