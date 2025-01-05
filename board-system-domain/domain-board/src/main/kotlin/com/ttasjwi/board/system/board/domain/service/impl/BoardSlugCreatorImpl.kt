package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.board.domain.service.BoardSlugCreator
import com.ttasjwi.board.system.core.annotation.component.DomainService

@DomainService
class BoardSlugCreatorImpl : BoardSlugCreator {

    override fun create(value: String): Result<BoardSlug> {
        return kotlin.runCatching {
            BoardSlug.create(value)
        }
    }
}
