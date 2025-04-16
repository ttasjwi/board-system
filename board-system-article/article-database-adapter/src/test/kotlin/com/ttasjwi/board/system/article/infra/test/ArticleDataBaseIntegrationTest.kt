package com.ttasjwi.board.system.article.infra.test

import com.ttasjwi.board.system.article.infra.persistence.ArticleCategoryPersistenceAdapter
import com.ttasjwi.board.system.article.infra.persistence.ArticlePersistenceAdapter
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
abstract class ArticleDataBaseIntegrationTest {

    @Autowired
    protected lateinit var articlePersistenceAdapter: ArticlePersistenceAdapter

    @Autowired
    protected lateinit var articleCategoryPersistenceAdapter: ArticleCategoryPersistenceAdapter

    @Autowired
    protected lateinit var entityManager: EntityManager

    protected fun flushAndClearEntityManager() {
        entityManager.flush()
        entityManager.clear()
    }
}
