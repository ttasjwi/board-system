package com.ttasjwi.board.system.articlelike.domain.processor

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleLikeCreateCommand
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleAlreadyLikedException
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleLikeNotAllowedException
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlelike.domain.model.Article
import com.ttasjwi.board.system.articlelike.domain.model.ArticleCategory
import com.ttasjwi.board.system.articlelike.domain.model.ArticleLike
import com.ttasjwi.board.system.articlelike.domain.model.ArticleLikeCount
import com.ttasjwi.board.system.articlelike.domain.port.ArticleCategoryPersistencePort
import com.ttasjwi.board.system.articlelike.domain.port.ArticleLikeCountPersistencePort
import com.ttasjwi.board.system.articlelike.domain.port.ArticleLikePersistencePort
import com.ttasjwi.board.system.articlelike.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class ArticleLikeCreateProcessor(
    private val articlePersistencePort: ArticlePersistencePort,
    private val articleCategoryPersistencePort: ArticleCategoryPersistencePort,
    private val articleLikePersistencePort: ArticleLikePersistencePort,
    private val articleLikeCountPersistencePort: ArticleLikeCountPersistencePort,
) {

    private val idGenerator: IdGenerator = IdGenerator.create()

    @Transactional
    fun like(command: ArticleLikeCreateCommand): ArticleLike {
        // 게시글 조회
        val article = getArticleOrThrow(command.articleId)

        // 게시글 카테고리 조회
        val articleCategory = getArticleCategoryOrThrow(article.articleCategoryId)

        // 카테고리 정책 확인 (좋아요 금지를 하진 않았는지...?)
        checkAllowLike(command.articleId, articleCategory)

        // 좋아요 존재 확인 (이미 좋아요했다면, 예외 발생)
        checkDuplicateLike(command.articleId, command.likeUser.userId)

        // 좋아요 생성 및 저장
        val articleLike = createArticleLikeAndSave(command)

        // 좋아요수 증가, 수정 반영
        upsertArticleLikeCount(command.articleId)
        return articleLike
    }

    private fun getArticleOrThrow(articleId: Long): Article {
        return articlePersistencePort.findByIdOrNull(articleId)
            ?: throw ArticleNotFoundException(articleId)
    }

    private fun getArticleCategoryOrThrow(articleCategoryId: Long): ArticleCategory {
        return articleCategoryPersistencePort.findByIdOrNull(articleCategoryId)
            ?: throw IllegalStateException("게시글 카테고리가 존재하지 않음 (articleCategoryId=$articleCategoryId)")
    }

    private fun checkAllowLike(articleId: Long, articleCategory: ArticleCategory) {
        if (!articleCategory.allowLike) {
            throw ArticleLikeNotAllowedException(articleId, articleCategory.articleCategoryId)
        }
    }

    private fun checkDuplicateLike(articleId: Long, userId: Long) {
        if (articleLikePersistencePort.existsByArticleIdAndUserId(articleId, userId)) {
            throw ArticleAlreadyLikedException(articleId, userId)
        }
    }

    private fun createArticleLikeAndSave(command: ArticleLikeCreateCommand): ArticleLike {
        val articleLike = ArticleLike.create(
            articleLikeId = idGenerator.nextId(),
            articleId = command.articleId,
            userId = command.likeUser.userId,
            currentTime = command.currentTime,
        )
        articleLikePersistencePort.save(articleLike)
        return articleLike
    }

    private fun upsertArticleLikeCount(articleId: Long) {
        articleLikeCountPersistencePort.increase(articleId)
    }
}
