package com.ttasjwi.board.system.auth.domain.model

/**
 * 회원 및 회원이 가진 리프레시 토큰 목록을 관리하는 객체입니다.
 */
class RefreshTokenHolder
internal constructor(
    val authMember: AuthMember,
    tokens: MutableMap<RefreshTokenId, RefreshToken>
) {

    private val _tokens: MutableMap<RefreshTokenId, RefreshToken> = tokens

    companion object {

        fun restore(
            memberId: Long,
            email: String,
            username: String,
            nickname: String,
            roleName: String,
            tokens: MutableMap<RefreshTokenId, RefreshToken>
        ): RefreshTokenHolder {
            return RefreshTokenHolder(
                authMember = AuthMember.restore(
                    memberId = memberId,
                    email = email,
                    username = username,
                    nickname = nickname,
                    roleName = roleName,
                ),
                tokens = tokens,
            )
        }
    }

    fun getTokens(): Map<RefreshTokenId, RefreshToken> {
        return _tokens.toMap()
    }
}
