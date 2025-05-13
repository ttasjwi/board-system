package com.ttasjwi.board.system.articlecomment.infra.test

import com.ttasjwi.board.system.articlecomment.infra.persistence.ArticleCommentPersistenceAdapter
import com.ttasjwi.board.system.articlecomment.infra.persistence.ArticleCommentWriterNicknamePersistenceAdapter
import com.ttasjwi.board.system.articlecomment.infra.persistence.ArticlePersistenceAdapter
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
    protected lateinit var articlePersistenceAdapter: ArticlePersistenceAdapter

    @Autowired
    protected lateinit var articleCommentWriterNicknamePersistenceAdapter: ArticleCommentWriterNicknamePersistenceAdapter

    @Autowired
    protected lateinit var entityManager: EntityManager

    protected fun flushAndClearEntityManager() {
        entityManager.flush()
        entityManager.clear()
    }
}
