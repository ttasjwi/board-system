package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.service.BoardNameCreator
import com.ttasjwi.board.system.common.annotation.component.DomainService

@DomainService
class BoardNameCreatorImpl : BoardNameCreator {
    override fun create(value: String): Result<BoardName> {
        return kotlin.runCatching {
            BoardName.create(value)
        }
    }
}
