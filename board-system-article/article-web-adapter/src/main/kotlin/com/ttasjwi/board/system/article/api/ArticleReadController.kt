package com.ttasjwi.board.system.article.api

import com.ttasjwi.board.system.article.domain.ArticleReadResponse
import com.ttasjwi.board.system.article.domain.ArticleReadUseCase
import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleReadController(
    private val articleReadUseCase: ArticleReadUseCase,
) {

    @PermitAll
    @GetMapping("/api/v1/articles/{articleId}")
    fun read(@PathVariable("articleId") articleId: Long): ResponseEntity<ArticleReadResponse> {
        val response = articleReadUseCase.read(articleId)
        return ResponseEntity.ok(response)
    }
}
