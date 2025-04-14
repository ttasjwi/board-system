package com.ttasjwi.board.system.board.domain.policy

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardArticleCategoryNameFormatException
import com.ttasjwi.board.system.common.annotation.component.DomainPolicy

@DomainPolicy
internal class BoardArticleCategoryNamePolicyImpl : BoardArticleCategoryNamePolicy {

    companion object {

        /**
         * 최대 20자
         */
        internal const val MAX_LENGTH = 20
    }

    override fun validate(name: String): Result<String> = kotlin.runCatching {
        if (name != name.trim() // 양끝 공백 허용 x
            || name.length > MAX_LENGTH
        ) {
            throw InvalidBoardArticleCategoryNameFormatException()
        }
        name
    }
}
