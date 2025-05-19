package com.ttasjwi.board.system.articlelike.api

import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCancelResponse
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCancelUseCase
import com.ttasjwi.board.system.common.annotation.auth.RequireAuthenticated
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleLikeCancelController(
    private val articleLikeCancelUseCase: ArticleLikeCancelUseCase,
) {

    @RequireAuthenticated
    @DeleteMapping("/api/v1/articles/{articleId}/likes")
    fun cancelLike(@PathVariable("articleId") articleId: Long): ResponseEntity<ArticleLikeCancelResponse> {
        val response = articleLikeCancelUseCase.cancelLike(articleId)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(response)
    }
}
