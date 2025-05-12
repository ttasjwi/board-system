package com.ttasjwi.board.system.articlecomment.infra.test

import com.ttasjwi.board.system.articlecomment.infra.persistence.ArticleCommentPersistenceAdapter
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class ArticleCommentDataBaseIntegrationTest {

    @Autowired
    protected lateinit var articleCommentPersistenceAdapter: ArticleCommentPersistenceAdapter

    @Autowired
    private lateinit var entityManager: EntityManager


    protected fun flushAndClearEntityManager() {
        entityManager.flush()
        entityManager.clear()
    }
}
