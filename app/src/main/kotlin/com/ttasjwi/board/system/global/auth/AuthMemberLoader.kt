package com.ttasjwi.board.system.global.auth

interface AuthMemberLoader {

    /**
     * 현재 인증회원을 조회합니다. 인증된 회원이 아닐 경우 null 이 반환됩니다.
     */
    fun loadCurrentAuthMember(): AuthMember?
}
