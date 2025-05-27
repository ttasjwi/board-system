package com.ttasjwi.board.system.articleread.domain.port

import com.ttasjwi.board.system.articleread.domain.model.ArticleDetail

interface ArticleDetailStorage {

    fun read(articleId: Long, userId: Long?): ArticleDetail?
}
