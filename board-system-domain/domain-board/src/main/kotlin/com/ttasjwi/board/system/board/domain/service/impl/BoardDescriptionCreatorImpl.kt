package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.board.domain.service.BoardDescriptionCreator
import com.ttasjwi.board.system.core.annotation.component.DomainService

@DomainService
class BoardDescriptionCreatorImpl : BoardDescriptionCreator {

    override fun create(value: String): Result<BoardDescription> {
        return kotlin.runCatching {
            BoardDescription.create(value)
        }
    }
}
