package com.ttasjwi.board.system.core.exception

/**
 * Null 이여서 안 되는 상황, 즉 어떤 필드값이 필수여야함을 나타내는 예외
 * @param source Null 이여서는 안되는 필드 또는 맥락의 이름
 *
 * @author ttasjwi
 * @since 2024/10/02
 */
class NullArgumentException(
    source: String,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.NullArgument",
    args = listOf(source),
    source = source,
    debugMessage = "${source}은(는) 필수입니다."
)
