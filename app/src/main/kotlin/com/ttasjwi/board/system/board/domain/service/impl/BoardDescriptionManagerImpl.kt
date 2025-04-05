package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardDescriptionFormatException
import com.ttasjwi.board.system.board.domain.service.BoardDescriptionManager
import com.ttasjwi.board.system.global.annotation.DomainService

@DomainService
class BoardDescriptionManagerImpl : BoardDescriptionManager {

    companion object {

        /**
         * 게시판 설명의 최대 글자 길이제한
         */
        internal const val MAX_LENGTH = 100
    }

    override fun create(boardDescription: String): Result<String> = kotlin.runCatching {
        if (boardDescription.length > MAX_LENGTH) {
            throw InvalidBoardDescriptionFormatException()
        }
        boardDescription
    }
}
