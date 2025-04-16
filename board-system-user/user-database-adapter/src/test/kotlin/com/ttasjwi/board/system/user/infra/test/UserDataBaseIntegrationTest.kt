package com.ttasjwi.board.system.user.infra.test

import com.ttasjwi.board.system.user.infra.persistence.UserPersistenceAdapter
import com.ttasjwi.board.system.user.infra.persistence.SocialConnectionPersistenceAdapter
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
abstract class UserDataBaseIntegrationTest {

    @Autowired
    protected lateinit var userPersistenceAdapter: UserPersistenceAdapter

    @Autowired
    protected lateinit var entityManager: EntityManager

    @Autowired
    protected lateinit var socialConnectionPersistenceAdapter: SocialConnectionPersistenceAdapter

    protected fun flushAndClearEntityManager() {
        entityManager.flush()
        entityManager.clear()
    }
}
