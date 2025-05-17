package com.ttasjwi.board.system.articlelike.infra.test

import com.ttasjwi.board.system.articlelike.infra.persistence.ArticleCategoryPersistenceAdapter
import com.ttasjwi.board.system.articlelike.infra.persistence.ArticleLikeCountPersistenceAdapter
import com.ttasjwi.board.system.articlelike.infra.persistence.ArticleLikePersistenceAdapter
import com.ttasjwi.board.system.articlelike.infra.persistence.ArticlePersistenceAdapter
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
abstract class ArticleLikeDataBaseIntegrationTest {

    @Autowired
    protected lateinit var articleLikeArticleCategoryPersistenceAdapter: ArticleCategoryPersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticleLikeCountPersistenceAdapter: ArticleLikeCountPersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticleLikePersistenceAdapter: ArticleLikePersistenceAdapter

    @Autowired
    protected lateinit var articleLikeArticlePersistenceAdapter: ArticlePersistenceAdapter

    @Autowired
    protected lateinit var entityManager: EntityManager

    protected fun flushAndClearEntityManager() {
        entityManager.flush()
        entityManager.clear()
    }
}
