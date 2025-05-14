package com.ttasjwi.board.system.articlecomment.domain.port

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment

interface ArticleCommentPersistencePort {

    fun save(articleComment: ArticleComment): ArticleComment
    fun findById(articleCommentId: Long): ArticleComment?

    fun findAllPage(articleId: Long, offset: Long, limit: Long): List<ArticleComment>
    fun count(articleId: Long, limit: Long): Long
}
