package com.ttasjwi.board.system.article.api

import com.ttasjwi.board.system.article.domain.ArticleUpdateRequest
import com.ttasjwi.board.system.article.domain.ArticleUpdateResponse
import com.ttasjwi.board.system.article.domain.ArticleUpdateUseCase
import com.ttasjwi.board.system.common.annotation.auth.RequireAuthenticated
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleUpdateController(
    private val articleUpdateUseCase: ArticleUpdateUseCase
) {

    @RequireAuthenticated
    @PutMapping("/api/v1/articles/{articleId}")
    fun update(
        @PathVariable("articleId") articleId: Long,
        @RequestBody request: ArticleUpdateRequest,
    ): ResponseEntity<ArticleUpdateResponse> {
        val response = articleUpdateUseCase.update(articleId, request)
        return ResponseEntity.ok(response)
    }
}
