package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.member.domain.model.SocialService
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser

fun socialServiceUserFixture(
    socialService: SocialService = SocialService.GOOGLE,
    socialServiceUserId: String = "1ser123456"
): SocialServiceUser {
    return SocialServiceUser(
        service = socialService,
        userId = socialServiceUserId
    )
}
