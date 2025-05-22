package com.ttasjwi.board.system.articleview.api

import com.ttasjwi.board.system.articleview.domain.ArticleViewCountIncreaseUseCase
import com.ttasjwi.board.system.common.annotation.auth.RequireAuthenticated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleViewCountIncreaseController(
    private val articleViewCountIncreaseUseCase: ArticleViewCountIncreaseUseCase
) {

    @RequireAuthenticated
    @PostMapping("/api/v1/articles/{articleId}/views")
    fun increaseViewCount(@PathVariable articleId: Long) {
        articleViewCountIncreaseUseCase.increase(articleId)
    }
}
