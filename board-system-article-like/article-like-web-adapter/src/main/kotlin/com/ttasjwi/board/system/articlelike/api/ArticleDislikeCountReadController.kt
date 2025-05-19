package com.ttasjwi.board.system.articlelike.api

import com.ttasjwi.board.system.articlelike.domain.ArticleDislikeCountReadResponse
import com.ttasjwi.board.system.articlelike.domain.ArticleDislikeCountReadUseCase
import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleDislikeCountReadController(
    private val articleDislikeCountReadUseCase: ArticleDislikeCountReadUseCase
) {

    @PermitAll
    @GetMapping("/api/v1/articles/{articleId}/dislikes/count")
    fun readDislikeCount(@PathVariable("articleId") articleId: Long): ResponseEntity<ArticleDislikeCountReadResponse> {
        val response = articleDislikeCountReadUseCase.readDislikeCount(articleId)
        return ResponseEntity.ok(response)
    }
}
