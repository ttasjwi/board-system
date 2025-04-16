package com.ttasjwi.board.system.board.api

import com.ttasjwi.board.system.board.domain.*
import com.ttasjwi.board.system.common.annotation.auth.RequireAuthenticated
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleCategoryCreateController(
    private val articleCategoryCreateUseCase: ArticleCategoryCreateUseCase
) {

    @RequireAuthenticated
    @PostMapping("/api/v1/boards/{boardId}/article-categories")
    fun createArticleCategory(@PathVariable boardId: Long, @RequestBody request: ArticleCategoryCreateRequest): ResponseEntity<ArticleCategoryCreateResponse> {
        val response = articleCategoryCreateUseCase.create(boardId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}
