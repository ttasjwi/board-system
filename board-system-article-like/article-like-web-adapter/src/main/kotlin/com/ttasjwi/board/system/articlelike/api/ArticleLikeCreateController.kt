package com.ttasjwi.board.system.articlelike.api

import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCreateRequest
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCreateResponse
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCreateUseCase
import com.ttasjwi.board.system.common.annotation.auth.RequireAuthenticated
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleLikeCreateController(
    private val articleLikeCreateUseCase: ArticleLikeCreateUseCase,
) {

    @RequireAuthenticated
    @PostMapping("/api/v1/article-likes")
    fun like(@RequestBody request: ArticleLikeCreateRequest): ResponseEntity<ArticleLikeCreateResponse> {
        val response = articleLikeCreateUseCase.like(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}
