package com.ttasjwi.board.system.articlecomment.domain.processor

import com.ttasjwi.board.system.articlecomment.domain.dto.ArticleCommentCreateCommand
import com.ttasjwi.board.system.articlecomment.domain.exception.*
import com.ttasjwi.board.system.articlecomment.domain.model.Article
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCategory
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCategoryPersistencePort
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentCountPersistencePort
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentPersistencePort
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentWriterNicknamePersistencePort
import com.ttasjwi.board.system.articlecomment.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class ArticleCommentCreateProcessor(
    private val articleCommentPersistencePort: ArticleCommentPersistencePort,
    private val articleCommentCountPersistencePort: ArticleCommentCountPersistencePort,
    private val articlePersistencePort: ArticlePersistencePort,
    private val articleCategoryPersistencePort: ArticleCategoryPersistencePort,
    private val articleCommentWriterNicknamePersistencePort: ArticleCommentWriterNicknamePersistencePort,
) {

    private val articleCommentIdGenerator: IdGenerator = IdGenerator.create()

    @Transactional
    fun create(command: ArticleCommentCreateCommand): ArticleComment {
        // 게시글 존재여부 확인 : 게시글이 없을 경우 예외 발생
        val article = getArticle(command.articleId)

        // 확장가능성 : 향후 게시글 soft delete 상태가 추가되었을 때, 삭제 상태인지 확인 필요

        // 게시글 카테고리 조회
        val articleCategory = getArticleCategory(article)

        // 게시글 카테고리 제약에 의해, 댓글을 작성할 수 없는 지 확인
        checkCommentWritable(article.articleId, articleCategory)

        // 부모댓글 아이디가 있다면 조회함. 유효한 부모댓글을 가져와야 함.
        val parentComment = getValidParentComment(command.parentCommentId, command.articleId)

        // 현재 댓글 작성자 닉네임 조회 : 없으면 탈퇴회원이지만 액세스토큰이 유효한 경우이므로 예외 발생
        val commentWriterNickname = findCommentWriterNickname(command.commentWriter.userId)

        // 댓글 생성 및 저장
        val articleComment = createArticleComment(command, parentComment, commentWriterNickname)
        articleCommentPersistencePort.save(articleComment)

        // 댓글 수 증가
        articleCommentCountPersistencePort.increase(command.articleId)

        return articleComment
    }

    /**
     * 게시글 조회
     */
    private fun getArticle(articleId: Long): Article {
        return articlePersistencePort.findById(articleId)
            ?: throw ArticleNotFoundException(articleId = articleId)
    }

    private fun getArticleCategory(article: Article): ArticleCategory {
        return articleCategoryPersistencePort.findByIdOrNull(article.articleCategoryId)
            ?: throw IllegalStateException("게시글 카테고리 조회 실패 : (articleId = ${article.articleId}, articleCategoryId= ${article.articleCategoryId})")
    }

    private fun checkCommentWritable(articleId: Long, articleCategory: ArticleCategory) {
        if (!articleCategory.allowComment) {
            throw ArticleCommentWriteNotAllowedException(articleId = articleId, articleCategoryId = articleCategory.articleCategoryId)
        }
    }

    /**
     * 부모댓글Id 값이 있을 경우 조회합니다.
     * 조회결과 부모댓글이 존재하지 않을 경우 예외가 발생합니다.
     * 부모댓글 Id 값이 없을 경우 조회하지 않습니다.
     *
     * 조회한 부모 댓글에 대해서 유효성을 확인합니다.
     */
    private fun getValidParentComment(parentCommentId: Long?, articleId: Long): ArticleComment? {
        if (parentCommentId == null) {
            return null
        }
        val parentComment = articleCommentPersistencePort.findById(parentCommentId)
            ?: throw ParentArticleCommentNotFoundException(parentCommentId)

        checkParentCommentValidity(parentComment, articleId)
        return parentComment
    }

    /**
     * 부모댓글의 유효성을 확인합니다.
     * 부모 댓글이 해당 게시글의 댓글이 아니거나,
     * 부모 댓글이 논리적으로 삭제된 상태일 경우 예외가 발생합니다.
     */
    private fun checkParentCommentValidity(parentComment: ArticleComment, articleId: Long) {
        // 부모 댓글이 해당 게시글의 댓글이 아니면 예외 발생
        if (!parentComment.belongsToArticle(articleId)) {
            throw InvalidParentArticleCommentException(parentComment.articleCommentId, articleId)
        }

        // 부모 댓글이 논리적으로 삭제된 경우 댓글 작성할 수 없음
        if (parentComment.isDeleted()) {
            throw ParentCommentAlreadyDeletedException(parentCommentId = parentComment.articleCommentId)
        }
    }

    /**
     * 댓글 작성자의 작성시점 닉네임 조회
     */
    private fun findCommentWriterNickname(commentWriterUserId: Long): String {
        return articleCommentWriterNicknamePersistencePort.findCommentWriterNickname(
            commentWriterId = commentWriterUserId
        ) ?: throw ArticleCommentWriterNicknameNotFoundException(
            articleCommentWriterId = commentWriterUserId
        )
    }

    /**
     * 댓글 생성
     */
    private fun createArticleComment(
        command: ArticleCommentCreateCommand,
        parentComment: ArticleComment?,
        commentWriterNickname: String
    ): ArticleComment {
        val articleCommentId = articleCommentIdGenerator.nextId()
        val articleComment = ArticleComment.create(
            articleCommentId = articleCommentId,
            content = command.content,
            articleId = command.articleId,
            rootParentCommentId = parentComment?.rootParentCommentId ?: articleCommentId,
            writerId = command.commentWriter.userId,
            writerNickname = commentWriterNickname,
            parentCommentWriterId = parentComment?.writerId,
            parentCommentWriterNickname = parentComment?.writerNickname,
            createdAt = command.currentTime
        )
        return articleComment
    }
}
