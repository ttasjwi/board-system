package com.ttasjwi.board.system.articleread.api

import com.ttasjwi.board.system.articleread.domain.ArticleDetailReadResponse
import com.ttasjwi.board.system.articleread.domain.ArticleDetailReadUseCase
import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleDetailReadController(
    private val articleDetailReadUseCase: ArticleDetailReadUseCase
) {

    @PermitAll
    @GetMapping("/api/v2/articles/{articleId}")
    fun read(@PathVariable("articleId") articleId: Long): ResponseEntity<ArticleDetailReadResponse> {
        val response = articleDetailReadUseCase.read(articleId)
        return ResponseEntity.ok(response)
    }
}
