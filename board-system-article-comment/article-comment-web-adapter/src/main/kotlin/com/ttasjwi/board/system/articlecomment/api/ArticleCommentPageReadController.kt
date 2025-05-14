package com.ttasjwi.board.system.articlecomment.api

import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentPageReadRequest
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentPageReadResponse
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentPageReadUseCase
import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleCommentPageReadController(
    private val useCase: ArticleCommentPageReadUseCase
) {

    @PermitAll
    @GetMapping("/api/v1/article-comments")
    fun readAllPage(@ModelAttribute request: ArticleCommentPageReadRequest): ResponseEntity<ArticleCommentPageReadResponse> {
        val response = useCase.readAllPage(request)
        return ResponseEntity.ok(response)
    }
}
