package com.ttasjwi.board.system.member.domain.processor

import com.ttasjwi.board.system.common.auth.RefreshTokenExpiredException
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.dto.TokenRefreshCommand
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import com.ttasjwi.board.system.member.domain.port.fixture.MemberRefreshTokenIdListPersistencePortFixture
import com.ttasjwi.board.system.member.domain.port.fixture.RefreshTokenIdPersistencePortFixture
import com.ttasjwi.board.system.member.domain.service.RefreshTokenHandler
import com.ttasjwi.board.system.member.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("TokenRefreshProcessor: 토큰 재갱신 애플리케이션 명령을 실질적으로 처리한다.")
class TokenRefreshProcessorTest {

    private lateinit var processor: TokenRefreshProcessor
    private lateinit var refreshTokenHandler: RefreshTokenHandler
    private lateinit var memberRefreshTokenIdListPersistencePortFixture: MemberRefreshTokenIdListPersistencePortFixture
    private lateinit var refreshTokenIdPersistencePortFixture: RefreshTokenIdPersistencePortFixture
    private lateinit var member: Member

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()

        refreshTokenHandler = container.refreshTokenHandler
        memberRefreshTokenIdListPersistencePortFixture = container.memberRefreshTokenIdListPersistencePortFixture
        refreshTokenIdPersistencePortFixture = container.refreshTokenIdPersistencePortFixture
        processor = container.tokenRefreshProcessor
        member = container.memberPersistencePortFixture.save(
            memberFixture(
                memberId = 145688L,
                role = Role.ADMIN,
            )
        )
    }

    @Test
    @DisplayName("리프레시토큰을 재갱신하지 않아도 될 경우 리프레시 토큰이 갱신되지 않고 액세스토큰만 갱신된다.")
    fun testRefreshTokenNotRefresh() {
        // given
        val refreshToken = refreshTokenHandler.createAndPersist(member.memberId, appDateTimeFixture())

        val currentTime = appDateTimeFixture(
            hour = (RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR
                    - RefreshTokenHandler.REFRESH_REQUIRE_THRESHOLD_HOURS).toInt()
        )
            .minusSeconds(1)

        val command = TokenRefreshCommand(refreshToken.tokenValue, currentTime)

        // when
        val (accessToken, newRefreshToken) = processor.tokenRefresh(command)

        // then
        val memberRefreshTokenIds = memberRefreshTokenIdListPersistencePortFixture.findAll(member.memberId)
        val isPrevRefreshTokenExists = refreshTokenIdPersistencePortFixture.exists(member.memberId, refreshToken.refreshTokenId)

        assertThat(accessToken.authMember.memberId).isEqualTo(refreshToken.memberId)
        assertThat(accessToken.authMember.role).isEqualTo(member.role)
        assertThat(accessToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(accessToken.expiresAt).isEqualTo(command.currentTime.plusMinutes(30))

        assertThat(newRefreshToken.memberId).isEqualTo(refreshToken.memberId)
        assertThat(newRefreshToken.refreshTokenId).isEqualTo(refreshToken.refreshTokenId)
        assertThat(newRefreshToken.tokenValue).isEqualTo(refreshToken.tokenValue)
        assertThat(newRefreshToken.issuedAt).isEqualTo(refreshToken.issuedAt)
        assertThat(newRefreshToken.expiresAt).isEqualTo(refreshToken.expiresAt)

        assertThat(memberRefreshTokenIds).containsExactly(refreshToken.refreshTokenId)
        assertThat(isPrevRefreshTokenExists).isTrue()
    }

    @Test
    @DisplayName("리프레시토큰을 재갱신해야할 경우 리프레시 토큰이 함께 재갱신된다.")
    fun testRefreshTokenRefresh() {
        // given
        val refreshToken = refreshTokenHandler.createAndPersist(member.memberId, appDateTimeFixture())

        val currentTime = appDateTimeFixture(
            hour = (RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR
                    - RefreshTokenHandler.REFRESH_REQUIRE_THRESHOLD_HOURS).toInt()
        )
            .plusSeconds(1)

        val command = TokenRefreshCommand(refreshToken.tokenValue, currentTime)

        // when
        val (accessToken, newRefreshToken) = processor.tokenRefresh(command)

        // then
        val memberRefreshTokenIds = memberRefreshTokenIdListPersistencePortFixture.findAll(member.memberId)
        val isPrevRefreshTokenExists = refreshTokenIdPersistencePortFixture.exists(member.memberId, refreshToken.refreshTokenId)
        val isNewRefreshTokenExists = refreshTokenIdPersistencePortFixture.exists(member.memberId, newRefreshToken.refreshTokenId)

        assertThat(accessToken.authMember.memberId).isEqualTo(refreshToken.memberId)
        assertThat(accessToken.authMember.role).isEqualTo(member.role)
        assertThat(accessToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(accessToken.expiresAt).isEqualTo(command.currentTime.plusMinutes(30))


        assertThat(newRefreshToken.memberId).isEqualTo(refreshToken.memberId)
        assertThat(newRefreshToken.refreshTokenId).isNotEqualTo(refreshToken.refreshTokenId)
        assertThat(newRefreshToken.tokenValue).isNotEqualTo(refreshToken.tokenValue)
        assertThat(newRefreshToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(newRefreshToken.expiresAt).isEqualTo(command.currentTime.plusHours(RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR))

        assertThat(memberRefreshTokenIds).containsExactly(newRefreshToken.refreshTokenId)
        assertThat(isPrevRefreshTokenExists).isFalse()
        assertThat(isNewRefreshTokenExists).isTrue()
    }

    @Test
    @DisplayName("리프레시토큰은 파싱됐으나, 회원이 존재하지 않을 경우 예외가 발생한다.")
    fun testMemberNotFound() {
        val refreshToken = refreshTokenHandler.createAndPersist(1343514131L, appDateTimeFixture())

        val command = TokenRefreshCommand(refreshToken.tokenValue, appDateTimeFixture(hour = 5))

        val ex = assertThrows<RefreshTokenExpiredException> {
            processor.tokenRefresh(command)
        }
        assertThat(ex.debugMessage).isEqualTo("회원이 존재하지 않아서, 리프레시토큰이 더 이상 유효하지 않음")
    }
}
