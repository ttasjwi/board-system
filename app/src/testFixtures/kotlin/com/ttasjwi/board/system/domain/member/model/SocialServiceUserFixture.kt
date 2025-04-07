package com.ttasjwi.board.system.domain.member.model

fun socialServiceUserFixture(
    socialService: SocialService = SocialService.GOOGLE,
    socialServiceUserId: String = "1ser123456"
): SocialServiceUser {
    return SocialServiceUser(
        service = socialService,
        userId = socialServiceUserId
    )
}
