package com.ttasjwi.board.system.member.domain.port.fixture

import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("MemberStorageFixture(Appender, Finder) 테스트")
class MemberPersistencePortFixtureTest {

    private lateinit var memberPersistencePortFixture: MemberPersistencePortFixture

    @BeforeEach
    fun init() {
        memberPersistencePortFixture = MemberPersistencePortFixture()
    }

    @Nested
    @DisplayName("save: 회원을 저장하고, id 를 발급받아 반환시킨다.")
    inner class SaveTest {

        @DisplayName("id가 없는 회원을 저장하면 id가 발급된다.")
        @Test
        fun test1() {
            // given
            val member = memberFixture()

            // when
            val savedMember = memberPersistencePortFixture.save(member)

            // then
            assertThat(savedMember.memberId).isNotNull
            assertThat(savedMember.email).isEqualTo(member.email)
            assertThat(savedMember.password).isEqualTo(member.password)
            assertThat(savedMember.username).isEqualTo(member.username)
            assertThat(savedMember.nickname).isEqualTo(member.nickname)
            assertThat(savedMember.role).isEqualTo(member.role)
            assertThat(savedMember.registeredAt).isEqualTo(member.registeredAt)
        }

        @DisplayName("id 가 있는 회원을 저장하고 조회하면 기존 회원 정보를 덮어쓴 채 조회된다.")
        @Test
        fun test2() {
            // given
            val savedMember = memberPersistencePortFixture.save(
                memberFixture(
                    password = "1111",
                )
            )

            val changedMember = memberPersistencePortFixture.save(
                memberFixture(
                    memberId = savedMember.memberId,
                    password = "2222",
                )
            )

            // when
            val findMember = memberPersistencePortFixture.findByIdOrNull(savedMember.memberId)!!

            // then
            assertThat(findMember.memberId).isEqualTo(savedMember.memberId)
            assertThat(findMember.memberId).isEqualTo(changedMember.memberId)
            assertThat(findMember.password).isEqualTo(changedMember.password)
        }

    }

    @Nested
    @DisplayName("findByIdOrNull: id 로 회원을 조회한다.")
    inner class FindByIdOrNullTest {

        @DisplayName("존재하는 회원 id로 조회하면 해당 회원이 조회됨")
        @Test
        fun test1() {
            // given
            val member = memberFixture()
            val savedMember = memberPersistencePortFixture.save(memberFixture())

            // when
            val findMember = memberPersistencePortFixture.findByIdOrNull(savedMember.memberId)!!


            // then
            assertThat(findMember).isNotNull
            assertThat(findMember.email).isEqualTo(member.email)
            assertThat(findMember.username).isEqualTo(member.username)
            assertThat(findMember.nickname).isEqualTo(member.nickname)
            assertThat(findMember.password).isEqualTo(member.password)
            assertThat(findMember.role).isEqualTo(member.role)
            assertThat(findMember.registeredAt).isEqualTo(member.registeredAt)
        }

        @DisplayName("존재하지 않는 id로 조회하면 null이 조회됨")
        @Test
        fun test2() {
            // given
            val memberId = 1557L

            // when
            val findMember = memberPersistencePortFixture.findByIdOrNull(memberId)

            // then
            assertThat(findMember).isNull()
        }
    }

    @Nested
    @DisplayName("existsByEmail: 이메일로 회원의 존재여부를 반환한다.")
    inner class ExistsByEmailTest {
        @DisplayName("id에 해당하는 회원이 존재하면 true가 반환됨")
        @Test
        fun test1() {
            // given
            val member = memberFixture()
            val savedMember = memberPersistencePortFixture.save(member)

            // when
            val isExist = memberPersistencePortFixture.existsByEmail(savedMember.email)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("이메일에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val email = "abcd@gmail.com"

            // when
            val isExist = memberPersistencePortFixture.existsByEmail(email)

            // then
            assertThat(isExist).isFalse()
        }
    }

    @Nested
    @DisplayName("existsByNickname: 닉네임으로 회원의 존재여부를 반환한다.")
    inner class ExistsByNicknameTest {

        @DisplayName("닉네임에 해당하는 회원이 존재하면 true가 반환됨")
        @Test
        fun test1() {
            // given
            val member = memberFixture()
            val savedMember = memberPersistencePortFixture.save(member)

            // when
            val isExist = memberPersistencePortFixture.existsByNickname(savedMember.nickname)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("닉네임에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val nickname = "페이커"

            // when
            val isExist = memberPersistencePortFixture.existsByNickname(nickname)

            // then
            assertThat(isExist).isFalse()
        }
    }

    @Nested
    @DisplayName("existsByUsername: 사용자아이디(username)에 해당하는 회원 존재여부를 반환한다.")
    inner class ExistsByUsernameTest {

        @DisplayName("Username에 해당하는 회원이 존재하면 true가 반환됨")
        @Test
        fun test1() {
            // given
            val member = memberFixture()
            val savedMember = memberPersistencePortFixture.save(member)

            // when
            val isExist = memberPersistencePortFixture.existsByUsername(savedMember.username)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("Username 에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val username = "abcd124"
            // when
            val isExist = memberPersistencePortFixture.existsByUsername(username)

            // then
            assertThat(isExist).isFalse()
        }
    }
}
