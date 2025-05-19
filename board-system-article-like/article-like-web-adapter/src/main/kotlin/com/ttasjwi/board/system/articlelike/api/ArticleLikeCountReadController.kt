package com.ttasjwi.board.system.articlelike.api

import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCountReadResponse
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCountReadUseCase
import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleLikeCountReadController(
    private val articleLikeCountReadUseCase: ArticleLikeCountReadUseCase
) {

    @PermitAll
    @GetMapping("/api/v1/articles/{articleId}/likes/count")
    fun readLikeCount(@PathVariable("articleId") articleId: Long): ResponseEntity<ArticleLikeCountReadResponse> {
        val response = articleLikeCountReadUseCase.readLikeCount(articleId)
        return ResponseEntity.ok(response)
    }
}
