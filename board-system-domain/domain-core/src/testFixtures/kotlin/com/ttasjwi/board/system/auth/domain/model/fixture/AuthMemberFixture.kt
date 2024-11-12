package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.member.domain.model.*
import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import com.ttasjwi.board.system.member.domain.model.fixture.nicknameFixture
import com.ttasjwi.board.system.member.domain.model.fixture.usernameFixture

fun authMemberFixture(
    memberId: Long = 1L,
    email: String = "test@gmail.com",
    username: String = "testuser",
    nickname: String = "테스트닉네임",
    role: Role = Role.USER,
): AuthMember {
    return TestAuthMember(
        memberId = memberIdFixture(memberId),
        email = emailFixture(email),
        username = usernameFixture(username),
        nickname = nicknameFixture(nickname),
        role = role,
    )
}

private class TestAuthMember(
    memberId: MemberId,
    email: Email,
    username: Username,
    nickname: Nickname,
    role: Role
) : AuthMember(
    memberId = memberId,
    email = email,
    username = username,
    nickname = nickname,
    role = role
)
