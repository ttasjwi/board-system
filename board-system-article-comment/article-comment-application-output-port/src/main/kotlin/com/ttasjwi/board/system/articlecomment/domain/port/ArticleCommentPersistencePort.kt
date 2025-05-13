package com.ttasjwi.board.system.articlecomment.domain.port

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment

interface ArticleCommentPersistencePort {

    fun save(articleComment: ArticleComment): ArticleComment
    fun findById(articleCommentId: Long): ArticleComment?
}
