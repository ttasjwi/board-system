package com.ttasjwi.board.system.articlelike.api

import com.ttasjwi.board.system.articlelike.domain.ArticleDislikeCreateResponse
import com.ttasjwi.board.system.articlelike.domain.ArticleDislikeCreateUseCase
import com.ttasjwi.board.system.common.annotation.auth.RequireAuthenticated
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleDislikeCreateController(
    private val articleDislikeCreateUseCase: ArticleDislikeCreateUseCase,
) {

    @RequireAuthenticated
    @PostMapping("/api/v1/articles/{articleId}/dislikes")
    fun dislike(@PathVariable("articleId") articleId: Long): ResponseEntity<ArticleDislikeCreateResponse> {
        val response = articleDislikeCreateUseCase.dislike(articleId)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}
