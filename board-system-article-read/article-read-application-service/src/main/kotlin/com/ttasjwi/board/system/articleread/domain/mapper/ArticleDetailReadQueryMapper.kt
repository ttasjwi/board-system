package com.ttasjwi.board.system.articleread.domain.mapper

import com.ttasjwi.board.system.articleread.domain.dto.ArticleDetailReadQuery
import com.ttasjwi.board.system.common.annotation.component.ApplicationQueryMapper
import com.ttasjwi.board.system.common.auth.AuthUserLoader

@ApplicationQueryMapper
internal class ArticleDetailReadQueryMapper(
    private val authUserLoader: AuthUserLoader,
) {

    fun mapToQuery(articleId: Long): ArticleDetailReadQuery {
        return ArticleDetailReadQuery(
            articleId = articleId,
            authUser = authUserLoader.loadCurrentAuthUser()
        )
    }
}
