package com.ttasjwi.board.system.common.auth

interface AuthUserLoader {

    /**
     * 현재 인증 사용자를 조회합니다. 인증된 사용자가 아닐 경우 null 이 반환됩니다.
     */
    fun loadCurrentAuthUser(): AuthUser?
}
