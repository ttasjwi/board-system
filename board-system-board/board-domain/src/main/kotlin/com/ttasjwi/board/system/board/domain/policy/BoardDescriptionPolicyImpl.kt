package com.ttasjwi.board.system.board.domain.policy

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardDescriptionFormatException
import com.ttasjwi.board.system.common.annotation.component.DomainPolicy

@DomainPolicy
internal class BoardDescriptionPolicyImpl : BoardDescriptionPolicy {

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
