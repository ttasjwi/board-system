package com.ttasjwi.board.system.articlecomment.api

import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCreateRequest
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCreateResponse
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCreateUseCase
import com.ttasjwi.board.system.common.annotation.auth.RequireAuthenticated
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleCommentCreateController(
    private val articleCommentCreateUseCase: ArticleCommentCreateUseCase,
) {

    @RequireAuthenticated
    @PostMapping("/api/v1/article-comments")
    fun create(@RequestBody request: ArticleCommentCreateRequest): ResponseEntity<ArticleCommentCreateResponse> {
        val response = articleCommentCreateUseCase.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}
