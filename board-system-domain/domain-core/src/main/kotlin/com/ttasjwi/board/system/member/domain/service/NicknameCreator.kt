package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.Nickname

interface NicknameCreator {

    fun create(value: String): Result<Nickname>
}
