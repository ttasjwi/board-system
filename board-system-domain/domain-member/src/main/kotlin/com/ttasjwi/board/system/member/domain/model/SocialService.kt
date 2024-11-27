package com.ttasjwi.board.system.member.domain.model

enum class SocialService {
    GOOGLE, KAKAO, NAVER;

    companion object {
        fun restore(name: String): SocialService {
            return SocialService.valueOf(name.uppercase())
        }
    }
}
