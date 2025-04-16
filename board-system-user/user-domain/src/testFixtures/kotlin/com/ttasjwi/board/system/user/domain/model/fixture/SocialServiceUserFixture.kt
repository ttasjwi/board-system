package com.ttasjwi.board.system.user.domain.model.fixture

import com.ttasjwi.board.system.user.domain.model.SocialService
import com.ttasjwi.board.system.user.domain.model.SocialServiceUser

fun socialServiceUserFixture(
    socialService: SocialService = SocialService.GOOGLE,
    socialServiceUserId: String = "1ser123456"
): SocialServiceUser {
    return SocialServiceUser(
        socialService = socialService,
        socialServiceUserId = socialServiceUserId
    )
}
