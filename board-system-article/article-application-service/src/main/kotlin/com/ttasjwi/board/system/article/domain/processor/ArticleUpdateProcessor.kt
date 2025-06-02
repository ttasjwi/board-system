package com.ttasjwi.board.system.article.domain.processor

import com.ttasjwi.board.system.article.domain.dto.ArticleUpdateCommand
import com.ttasjwi.board.system.article.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.article.domain.exception.ArticleUpdateNotAllowedByCategoryRuleException
import com.ttasjwi.board.system.article.domain.exception.ArticleUpdateNotAllowedByWriterMismatchException
import com.ttasjwi.board.system.article.domain.model.Article
import com.ttasjwi.board.system.article.domain.model.ArticleCategory
import com.ttasjwi.board.system.article.domain.port.ArticleCategoryPersistencePort
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class ArticleUpdateProcessor(
    private val articlePersistencePort: ArticlePersistencePort,
    private val articleCategoryPersistencePort: ArticleCategoryPersistencePort
) {

    @Transactional
    fun update(command: ArticleUpdateCommand): Article {
        // 게시글 조회
        val article = getArticle(command.articleId)

        // 소유자 여부 확인
        checkAuthority(article, command.authUser.userId)

        // 게시글 카테고리 조회
        val articleCategory = getArticleCategory(command.articleId, article.articleCategoryId)

        // 게시글 수정 정책 확인
        checkUpdatable(article.articleId, articleCategory)

        // 게시글 수정
        val updateResult = article.update(command.title, command.content, command.currentTime)

        // 실제 수정되지 않았다면 게시글을 그대로 반환, 수정됐다면 저장 후 반환
        // 확장가능성 : 실제 수정됐다면 게시글 저장 후, 수정됐다는 이벤트를 발행하기?
        return when (updateResult) {
            Article.UpdateResult.UNCHANGED -> article
            Article.UpdateResult.CHANGED -> {
                articlePersistencePort.save(article)
                article
            }
        }
    }

    private fun getArticle(articleId: Long): Article {
        return articlePersistencePort.findByIdOrNull(articleId)
            ?: throw ArticleNotFoundException(source = "articleId", argument = articleId)
    }

    private fun checkAuthority(article: Article, userId: Long) {
        if (!article.isWriter(userId)) {
            throw ArticleUpdateNotAllowedByWriterMismatchException(article.articleId, userId)
        }
    }

    private fun getArticleCategory(articleId: Long, articleCategoryId: Long): ArticleCategory {
        // 게시글의 카테고리는 반드시 존재해야하므로,
        // 만약 게시글 카테고리가 조회 안 됐다면 서버 내부 예외로 간주
        return articleCategoryPersistencePort.findByIdOrNull(articleCategoryId = articleCategoryId)
            ?: throw IllegalStateException("게시글 카테고리 조회 실패! (articleId=$articleId, articleCategoryId=$articleCategoryId)")
    }

    private fun checkUpdatable(articleId: Long, articleCategory: ArticleCategory) {
        if (!articleCategory.allowSelfEditDelete) {
            throw ArticleUpdateNotAllowedByCategoryRuleException(
                articleId = articleId,
                articleCategoryId = articleCategory.articleCategoryId,
            )
        }
    }
}
