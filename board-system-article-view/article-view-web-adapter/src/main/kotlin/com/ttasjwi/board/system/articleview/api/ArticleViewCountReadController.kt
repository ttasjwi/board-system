package com.ttasjwi.board.system.articleview.api

import com.ttasjwi.board.system.articleview.domain.ArticleViewCountReadResponse
import com.ttasjwi.board.system.articleview.domain.ArticleViewCountReadUseCase
import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleViewCountReadController(
    private val articleViewCountReadUseCase: ArticleViewCountReadUseCase
) {

    @PermitAll
    @GetMapping("/api/v1/articles/{articleId}/views/count")
    fun readViewCount(@PathVariable articleId: Long): ResponseEntity<ArticleViewCountReadResponse> {
        val response = articleViewCountReadUseCase.readViewCount(articleId)
        return ResponseEntity.ok(response)
    }
}
