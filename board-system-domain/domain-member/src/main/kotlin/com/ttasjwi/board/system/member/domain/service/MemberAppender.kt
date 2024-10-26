package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.Member

interface MemberAppender {

    /**
     * 회원을 저장합니다. 저장된 회원은 유지되어야 하며, 저장된 회원에는 id가 발급됩니다.
     */
    fun save(member: Member): Member
}
