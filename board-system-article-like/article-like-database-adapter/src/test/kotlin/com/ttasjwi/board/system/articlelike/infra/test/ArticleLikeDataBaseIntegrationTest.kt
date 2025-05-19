package com.ttasjwi.board.system.articlelike.infra.test

import com.ttasjwi.board.system.articlelike.infra.persistence.*
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
abstract class ArticleLikeDataBaseIntegrationTest {

    @Autowired
    protected lateinit var articleLikeArticleLikePersistenceAdapter: ArticleLikePersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticleDislikePersistenceAdapter: ArticleDislikePersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticleLikeCountPersistenceAdapter: ArticleLikeCountPersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticleDislikeCountPersistenceAdapter: ArticleDislikeCountPersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticleCategoryPersistenceAdapter: ArticleCategoryPersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticlePersistenceAdapter: ArticlePersistenceAdapter

    @Autowired
    protected lateinit var entityManager: EntityManager

    protected fun flushAndClearEntityManager() {
        entityManager.flush()
        entityManager.clear()
    }
}
