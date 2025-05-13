package com.ttasjwi.board.system.articlecomment.domain.policy

import com.ttasjwi.board.system.articlecomment.domain.exception.InvalidArticleCommentContentFormatException
import com.ttasjwi.board.system.common.annotation.component.DomainPolicy

@DomainPolicy
class ArticleCommentContentPolicyImpl : ArticleCommentContentPolicy {

    companion object {
        internal const val MAX_LENGTH = 3000
    }

    override fun validate(content: String): Result<String> = kotlin.runCatching {
        if (content.length > MAX_LENGTH) {
            throw InvalidArticleCommentContentFormatException()
        }
        content
    }
}
