package com.ttasjwi.board.system.articlelike.api

import com.ttasjwi.board.system.articlelike.domain.ArticleDislikeCancelResponse
import com.ttasjwi.board.system.articlelike.domain.ArticleDislikeCancelUseCase
import com.ttasjwi.board.system.common.annotation.auth.RequireAuthenticated
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleDislikeCancelController(
    private val articleDislikeCancelUseCase: ArticleDislikeCancelUseCase,
) {

    @RequireAuthenticated
    @DeleteMapping("/api/v1/articles/{articleId}/dislikes")
    fun cancelLike(@PathVariable("articleId") articleId: Long): ResponseEntity<ArticleDislikeCancelResponse> {
        val response = articleDislikeCancelUseCase.cancelDislike(articleId)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(response)
    }
}
