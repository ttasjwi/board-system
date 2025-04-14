package com.ttasjwi.board.system.board.domain.policy

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardSlugFormatException
import com.ttasjwi.board.system.common.annotation.component.DomainPolicy

@DomainPolicy
internal class BoardSlugPolicyImpl : BoardSlugPolicy {

    companion object {
        /**
         * 영어 소문자 또는 숫자
         */
        private val pattern = Regex("^[a-z0-9]+\$")

        /**
         * 최소 길이 제한
         */
        internal const val MIN_LENGTH = 4

        /**
         * 최대 길이 제한
         */
        internal const val MAX_LENGTH = 20
    }

    override fun validate(boardSlug: String): Result<String> = kotlin.runCatching {
        if (boardSlug.length < MIN_LENGTH || boardSlug.length > MAX_LENGTH || !pattern.matches(boardSlug)) {
            throw InvalidBoardSlugFormatException()
        }
        boardSlug
    }
}
