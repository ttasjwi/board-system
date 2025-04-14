package com.ttasjwi.board.system.board.domain.policy

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardArticleCategorySlugFormatException
import com.ttasjwi.board.system.common.annotation.component.DomainPolicy

@DomainPolicy
internal class BoardArticleCategorySlugPolicyImpl : BoardArticleCategorySlugPolicy {

    companion object {

        /**
         * 최소 1자
         */
        const val MIN_LENGTH = 1

        /**
         * 최대 8자
         */
        const val MAX_LENGTH = 8

        /**
         * 영어 대소문자, 숫자만 허용.
         */
        val PATTERN = Regex("^[A-Za-z0-9]+\$")
    }

    override fun validate(slug: String): Result<String> = kotlin.runCatching {
        if (slug.length < MIN_LENGTH || slug.length > MAX_LENGTH || !slug.matches(PATTERN)) {
            throw InvalidBoardArticleCategorySlugFormatException()
        }
        slug
    }
}
