package com.ttasjwi.board.system.test

import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
abstract class DataBaseIntegrationTest {

    @Autowired
    protected lateinit var userUserPersistenceAdapter: com.ttasjwi.board.system.user.infra.persistence.UserPersistenceAdapter

    @Autowired
    protected lateinit var userSocialConnectionPersistenceAdapter: com.ttasjwi.board.system.user.infra.persistence.SocialConnectionPersistenceAdapter

    @Autowired
    protected lateinit var boardBoardPersistenceAdapter: com.ttasjwi.board.system.board.infra.persistence.BoardPersistenceAdapter

    @Autowired
    protected lateinit var boardArticleCategoryPersistenceAdapter: com.ttasjwi.board.system.board.infra.persistence.ArticleCategoryPersistenceAdapter

    @Autowired
    protected lateinit var articleArticlePersistenceAdapter: com.ttasjwi.board.system.article.infra.persistence.ArticlePersistenceAdapter

    @Autowired
    protected lateinit var articleArticleCategoryPersistenceAdapter: com.ttasjwi.board.system.article.infra.persistence.ArticleCategoryPersistenceAdapter

    @Autowired
    protected lateinit var articleArticleWriterNicknamePersistenceAdapter: com.ttasjwi.board.system.article.infra.persistence.ArticleWriterNicknamePersistenceAdapter

    @Autowired
    protected lateinit var articleCommentArticleCommentPersistenceAdapter: com.ttasjwi.board.system.articlecomment.infra.persistence.ArticleCommentPersistenceAdapter

    @Autowired
    protected lateinit var articleCommentArticlePersistenceAdapter: com.ttasjwi.board.system.articlecomment.infra.persistence.ArticlePersistenceAdapter

    @Autowired
    protected lateinit var articleCommentArticleCommentCountPersistenceAdapter: com.ttasjwi.board.system.articlecomment.infra.persistence.ArticleCommentCountPersistenceAdapter

    @Autowired
    protected lateinit var articleCommentArticleCommentWriterNicknamePersistenceAdapter: com.ttasjwi.board.system.articlecomment.infra.persistence.ArticleCommentWriterNicknamePersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticleLikePersistenceAdapter: com.ttasjwi.board.system.articlelike.infra.persistence.ArticleLikePersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticleDislikePersistenceAdapter: com.ttasjwi.board.system.articlelike.infra.persistence.ArticleDislikePersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticleLikeCountPersistenceAdapter: com.ttasjwi.board.system.articlelike.infra.persistence.ArticleLikeCountPersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticleDislikeCountPersistenceAdapter: com.ttasjwi.board.system.articlelike.infra.persistence.ArticleDislikeCountPersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticleCategoryPersistenceAdapter: com.ttasjwi.board.system.articlelike.infra.persistence.ArticleCategoryPersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticlePersistenceAdapter: com.ttasjwi.board.system.articlelike.infra.persistence.ArticlePersistenceAdapter

    @Autowired
    protected lateinit var articleViewArticlePersistenceAdapter: com.ttasjwi.board.system.articleview.infra.persistence.ArticlePersistenceAdapter

    @Autowired
    protected lateinit var articleReadArticleDetailStorage: com.ttasjwi.board.system.articleread.infra.persistence.ArticleDetailStorageImpl

    @Autowired
    protected lateinit var articleReadArticleSummaryStorage: com.ttasjwi.board.system.articleread.infra.persistence.ArticleSummaryStorageImpl

    @Autowired
    protected lateinit var entityManager: EntityManager

    protected fun flushAndClearEntityManager() {
        entityManager.flush()
        entityManager.clear()
    }
}
