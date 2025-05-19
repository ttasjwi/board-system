package com.ttasjwi.board.system.articlelike.api

import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCreateResponse
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCreateUseCase
import com.ttasjwi.board.system.common.annotation.auth.RequireAuthenticated
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleLikeCreateController(
    private val articleLikeCreateUseCase: ArticleLikeCreateUseCase,
) {

    @RequireAuthenticated
    @PostMapping("/api/v1/articles/{articleId}/likes")
    fun like(@PathVariable("articleId") articleId: Long): ResponseEntity<ArticleLikeCreateResponse> {
        val response = articleLikeCreateUseCase.like(articleId)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}
