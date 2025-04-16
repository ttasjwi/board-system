package com.ttasjwi.board.system.article.api

import com.ttasjwi.board.system.article.domain.ArticleCreateRequest
import com.ttasjwi.board.system.article.domain.ArticleCreateResponse
import com.ttasjwi.board.system.article.domain.ArticleCreateUseCase
import com.ttasjwi.board.system.common.annotation.auth.RequireAuthenticated
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleCreateController(
    private val articleCreateUseCase: ArticleCreateUseCase
) {

    @RequireAuthenticated
    @PostMapping("/api/v1/articles")
    fun create(@RequestBody articleCreateRequest: ArticleCreateRequest): ResponseEntity<ArticleCreateResponse> {
        val response = articleCreateUseCase.create(articleCreateRequest)
        return ResponseEntity(response, HttpStatus.CREATED)
    }
}
