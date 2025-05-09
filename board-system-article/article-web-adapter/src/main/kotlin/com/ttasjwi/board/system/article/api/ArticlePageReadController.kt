package com.ttasjwi.board.system.article.api

import com.ttasjwi.board.system.article.domain.ArticlePageReadRequest
import com.ttasjwi.board.system.article.domain.ArticlePageReadResponse
import com.ttasjwi.board.system.article.domain.ArticlePageReadUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticlePageReadController(
    private val articlePageReadUseCase: ArticlePageReadUseCase,
) {

    @GetMapping("/api/v1/articles")
    fun readAllPage(@ModelAttribute request: ArticlePageReadRequest): ResponseEntity<ArticlePageReadResponse> {
        val response = articlePageReadUseCase.readAllPage(request)
        return ResponseEntity.ok(response)
    }
}
