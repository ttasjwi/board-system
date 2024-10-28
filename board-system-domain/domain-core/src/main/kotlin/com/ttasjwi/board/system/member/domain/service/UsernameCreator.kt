package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.Username

interface UsernameCreator {

    fun create(value: String): Result<Username>
}
