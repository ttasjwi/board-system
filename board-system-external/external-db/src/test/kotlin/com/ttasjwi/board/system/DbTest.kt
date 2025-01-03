package com.ttasjwi.board.system

import com.ttasjwi.board.system.board.domain.external.db.BoardStorageImpl
import com.ttasjwi.board.system.member.domain.external.db.MemberStorageImpl
import com.ttasjwi.board.system.member.domain.service.SocialConnectionStorage
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
abstract class DbTest {

    @Autowired
    protected lateinit var memberStorageImpl: MemberStorageImpl

    @Autowired
    protected lateinit var socialConnectionStorage: SocialConnectionStorage

    @Autowired
    protected lateinit var boardStorageImpl: BoardStorageImpl

    @Autowired
    private lateinit var em: EntityManager

    protected fun flushAndClearEntityManager() {
        em.flush()
        em.clear()
    }
}
