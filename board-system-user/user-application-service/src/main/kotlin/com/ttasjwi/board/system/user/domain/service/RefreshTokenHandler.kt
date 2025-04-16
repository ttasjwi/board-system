package com.ttasjwi.board.system.user.domain.service

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.common.auth.RefreshTokenExpiredException
import com.ttasjwi.board.system.common.auth.RefreshTokenGeneratePort
import com.ttasjwi.board.system.common.auth.RefreshTokenParsePort
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.port.MemberRefreshTokenIdListPersistencePort
import com.ttasjwi.board.system.user.domain.port.RefreshTokenIdPersistencePort

@DomainService
class RefreshTokenHandler(
    private val refreshTokenGeneratePort: RefreshTokenGeneratePort,
    private val refreshTokenParsePort: RefreshTokenParsePort,
    private val memberRefreshTokenIdListPersistencePort: MemberRefreshTokenIdListPersistencePort,
    private val refreshTokenIdPersistencePort: RefreshTokenIdPersistencePort,
) {

    private val refreshTokenIdGenerator: IdGenerator = IdGenerator.create()

    companion object {
        internal const val REFRESH_TOKEN_VALIDITY_HOUR = 24L
        internal const val REFRESH_REQUIRE_THRESHOLD_HOURS = 8L // 리프레시 토큰 재갱신 기준점
        internal const val MAX_MEMBER_REFRESH_TOKEN_COUNT = 5
    }

    /**
     * 리프레시토큰을 생성하고, 저장소에 보관합니다.
     */
    fun createAndPersist(memberId: Long, issuedAt: AppDateTime): RefreshToken {
        val refreshToken = generate(memberId, issuedAt)
        save(refreshToken)
        return refreshToken
    }

    /**
     * 토큰값으로부터 리프레시토큰 인스턴스를 획득합니다.
     */
    fun parse(tokenValue: String): RefreshToken {
        return refreshTokenParsePort.parse(tokenValue)
    }

    /**
     * 리프레시토큰이 현재 만료됐다면 예외를 발생합니다.
     */
    fun throwIfExpired(refreshToken: RefreshToken, currentTime: AppDateTime) {
        if (refreshToken.isExpired(currentTime)) {
            memberRefreshTokenIdListPersistencePort.remove(refreshToken.memberId, refreshToken.refreshTokenId)
            throw RefreshTokenExpiredException("리프레시 토큰 만료시간이 경과되어 만료됨")
        }

        // 회원 리프레시토큰 Id 목록에 있는 지 확인(로그아웃, 동시 유지 가능 갯수 제한 등으로 인해 사라졌을 수 있음)
        // 없다면, 예외 발생
        if (!memberRefreshTokenIdListPersistencePort.exists(refreshToken.memberId, refreshToken.refreshTokenId)) {
            throw RefreshTokenExpiredException("리프레시 토큰이 로그아웃, 재갱신, 동시 토큰제약 등의 이유로 만료됨")
        }
    }

    /**
     * 리프레시토큰이 현재 갱신이 필요하다면 갱신해서 새로운 토큰을 반환하고, 필요 없다면 토큰을 그대로 반환합니다.
     */
    fun refreshIfRequired(refreshToken: RefreshToken, currentTime: AppDateTime): RefreshToken {
        if (currentTime >= refreshToken.expiresAt.minusHours(REFRESH_REQUIRE_THRESHOLD_HOURS)) {
            remove(refreshToken.memberId, refreshToken.refreshTokenId)
            return createAndPersist(refreshToken.memberId, currentTime)
        }
        return refreshToken
    }

    private fun generate(memberId: Long, issuedAt: AppDateTime): RefreshToken {
        return refreshTokenGeneratePort.generate(
            memberId = memberId,
            refreshTokenId = refreshTokenIdGenerator.nextId(),
            issuedAt = issuedAt,
            expiresAt = issuedAt.plusHours(REFRESH_TOKEN_VALIDITY_HOUR)
        )
    }

    private fun save(refreshToken: RefreshToken) {
        val memberId = refreshToken.memberId

        // 회원의 기존 토큰 ID 목록 조회
        val refreshTokenIds = memberRefreshTokenIdListPersistencePort.findAll(memberId)

        // 회원 리프레시토큰 아이디 목록에는 존재하지만,
        // 실제 존재하지 않는 경우 회원 리프레시토큰 아이디 목록에서 제거
        val idCount = refreshTokenIds.size
        var deleteCount = 0
        for (tokenId in refreshTokenIds) {
            if (!refreshTokenIdPersistencePort.exists(memberId, tokenId)) {
                memberRefreshTokenIdListPersistencePort.remove(memberId, tokenId)
                deleteCount++
            }
        }

        // 리프레시토큰 갯수 제한에 도달했고,
        // 위 과정을 거쳤음에도 삭제된 토큰이 없다면(모든 토큰의 유효기간이 남아있음)
        // 가장 오래된 토큰을 제거
        if (idCount == MAX_MEMBER_REFRESH_TOKEN_COUNT && deleteCount == 0) {
            remove(memberId, refreshTokenIds[0])
        }

        // 새로운 토큰 저장
        memberRefreshTokenIdListPersistencePort.add(
            memberId, refreshToken.refreshTokenId, MAX_MEMBER_REFRESH_TOKEN_COUNT.toLong()
        )
        refreshTokenIdPersistencePort.save(
            memberId, refreshToken.refreshTokenId, refreshToken.expiresAt
        )
    }

    private fun remove(memberId: Long, refreshTokenId: Long) {
        memberRefreshTokenIdListPersistencePort.remove(memberId, refreshTokenId)
        refreshTokenIdPersistencePort.remove(memberId, refreshTokenId)
    }
}
