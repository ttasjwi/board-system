package com.ttasjwi.board.system.article.domain.policy

import com.ttasjwi.board.system.article.domain.exception.InvalidArticleContentFormatException
import com.ttasjwi.board.system.common.annotation.component.DomainPolicy

@DomainPolicy
class ArticleContentPolicyImpl : ArticleContentPolicy {

    companion object {
        internal const val MAX_LENGTH = 3000
    }

    override fun validate(content: String): Result<String> = kotlin.runCatching {
        if (content.length > MAX_LENGTH) {
            throw InvalidArticleContentFormatException()
        }
        content
    }
}
