package com.ttasjwi.board.system.user.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class UnsupportedSocialServiceIdException(
    socialServiceId: String,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.UnsupportedSocialServiceId",
    args = listOf(socialServiceId),
    source = "socialServiceId",
    debugMessage = "우리 서비스에서 연동하는 소셜서비스 id가 아닙니다. (socialServiceId= ${socialServiceId})",
)
