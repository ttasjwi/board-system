package com.ttasjwi.board.system.article.domain.policy

import com.ttasjwi.board.system.article.domain.exception.InvalidArticleTitleFormatException
import com.ttasjwi.board.system.common.annotation.component.DomainPolicy

@DomainPolicy
class ArticleTitlePolicyImpl : ArticleTitlePolicy {

    companion object {
        internal const val MAX_LENGTH = 50
    }

    override fun validate(title: String): Result<String> = kotlin.runCatching {
        if (title.isBlank() || title.length > MAX_LENGTH) {
            throw InvalidArticleTitleFormatException()
        }
        title
    }
}
