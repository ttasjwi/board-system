package com.ttasjwi.board.system.member.infra.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate


@SpringBootTest
abstract class MemberRedisAdapterTest {

    @Autowired
    protected lateinit var emailVerificationPersistenceAdapter: EmailVerificationPersistenceAdapter

    @Autowired
    protected lateinit var memberRefreshTokenIdListPersistenceAdapter: MemberRefreshTokenIdListPersistenceAdapter

    @Autowired
    protected lateinit var refreshTokenIdPersistenceAdapter: RefreshTokenIdPersistenceAdapter

    @Autowired
    protected lateinit var redisTemplate: StringRedisTemplate
}
