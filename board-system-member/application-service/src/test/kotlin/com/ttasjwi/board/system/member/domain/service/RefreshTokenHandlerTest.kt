package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.common.auth.RefreshTokenGeneratePort
import com.ttasjwi.board.system.common.auth.RefreshTokenParsePort
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.port.MemberRefreshTokenIdListPersistencePort
import com.ttasjwi.board.system.member.domain.port.RefreshTokenIdPersistencePort
import com.ttasjwi.board.system.member.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("RefreshTokenHandler: 리프레시토큰 처리기")
class RefreshTokenHandlerTest {

    private lateinit var refreshTokenHandler: RefreshTokenHandler
    private lateinit var refreshTokenGeneratePort: RefreshTokenGeneratePort
    private lateinit var refreshTokenParsePort: RefreshTokenParsePort
    private lateinit var memberRefreshTokenIdListPersistencePort: MemberRefreshTokenIdListPersistencePort
    private lateinit var refreshTokenIdPersistencePort: RefreshTokenIdPersistencePort

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        refreshTokenHandler = container.refreshTokenHandler
        refreshTokenGeneratePort = container.refreshTokenPortFixture
        refreshTokenParsePort = container.refreshTokenPortFixture
        memberRefreshTokenIdListPersistencePort = container.memberRefreshTokenIdListPersistencePortFixture
        refreshTokenIdPersistencePort = container.refreshTokenIdPersistencePortFixture
    }

    @Nested
    @DisplayName("createAndPersist: 토큰 생성 및 저장")
    inner class CreateAndPersistTest {

        @Test
        @DisplayName("생성 후 저장된다.")
        fun simpleSuccess() {
            val memberId = 1L
            val issuedAt = appDateTimeFixture(dayOfMonth = 1)

            val refreshToken = refreshTokenHandler.createAndPersist(memberId, issuedAt)

            assertThat(refreshToken.memberId).isEqualTo(memberId)
            assertThat(refreshToken.refreshTokenId).isNotNull()
            assertThat(refreshToken.tokenValue).isNotNull()
        }
    }

}
