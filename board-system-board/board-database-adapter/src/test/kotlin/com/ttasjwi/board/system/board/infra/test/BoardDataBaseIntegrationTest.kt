package com.ttasjwi.board.system.board.infra.test

import com.ttasjwi.board.system.board.infra.persistence.BoardArticleCategoryPersistenceAdapter
import com.ttasjwi.board.system.board.infra.persistence.BoardPersistenceAdapter
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
abstract class BoardDataBaseIntegrationTest {

    @Autowired
    protected lateinit var boardPersistenceAdapter: BoardPersistenceAdapter

    @Autowired
    protected lateinit var boardArticleCategoryPersistenceAdapter: BoardArticleCategoryPersistenceAdapter

    @Autowired
    private lateinit var entityManager: EntityManager


    protected fun flushAndClearEntityManager() {
        entityManager.flush()
        entityManager.clear()
    }
}
