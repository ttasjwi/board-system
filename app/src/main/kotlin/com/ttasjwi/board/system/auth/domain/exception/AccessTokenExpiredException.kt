package com.ttasjwi.board.system.auth.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus
import java.time.ZonedDateTime

class AccessTokenExpiredException(
    expiredAt: ZonedDateTime,
    currentTime: ZonedDateTime
) : CustomException(
    status = ErrorStatus.UNAUTHENTICATED,
    code = "Error.AccessTokenExpired",
    args = listOf(expiredAt, currentTime),
    source = "accessToken",
    debugMessage = "액세스 토큰의 유효시간이 경과되어 더 이상 유효하지 않습니다. (expiredAt=${expiredAt},currentTime=${currentTime}) 리프레시 토큰을 통해 갱신해주세요. 리프레시 토큰도 만료됐다면 로그인을 다시 하셔야합니다."
)
