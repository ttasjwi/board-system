package com.ttasjwi.board.system.article.domain.processor

import com.ttasjwi.board.system.article.domain.dto.ArticleCreateCommand
import com.ttasjwi.board.system.article.domain.exception.ArticleCategoryNotFoundException
import com.ttasjwi.board.system.article.domain.model.Article
import com.ttasjwi.board.system.article.domain.model.ArticleCategory
import com.ttasjwi.board.system.article.domain.port.ArticleCategoryPersistencePort
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class ArticleCreateProcessor(
    private val articlePersistencePort: ArticlePersistencePort,
    private val articleCategoryPersistencePort: ArticleCategoryPersistencePort
) {

    private val articleIdGenerator: IdGenerator = IdGenerator.create()

    @Transactional
    fun create(command: ArticleCreateCommand): Article {
        // 게시글 카테고리 조회
        val articleCategory = getArticleCategoryOrThrow(command.articleCategoryId)

        // 향후 기능 확장 포인트 : 권한 체크
        // 사용자가 혹시 게시판에서 차단됐거나, 해당 카테고리에 대해서 글을 작성할 권한이 없거나?


        // 게시글 생성, 저장
        val article = createAndPersist(articleCategory, command)

        return article
    }

    private fun getArticleCategoryOrThrow(articleCategoryId: Long): ArticleCategory {
        return articleCategoryPersistencePort.findByIdOrNull(articleCategoryId)
            ?: throw ArticleCategoryNotFoundException(
                source = "articleCategoryId",
                argument = articleCategoryId
            )
    }

    private fun createAndPersist(articleCategory: ArticleCategory, command: ArticleCreateCommand): Article {
        val article = Article.create(
            articleId = articleIdGenerator.nextId(),
            title = command.title,
            content = command.content,
            boardId = articleCategory.boardId,
            articleCategoryId = articleCategory.articleCategoryId,
            writerId = command.writer.memberId,
            createdAt = command.currentTime
        )
        articlePersistencePort.save(article)
        return article
    }
}
