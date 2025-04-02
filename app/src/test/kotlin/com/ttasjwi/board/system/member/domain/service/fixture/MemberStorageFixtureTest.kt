package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import com.ttasjwi.board.system.member.domain.model.fixture.nicknameFixture
import com.ttasjwi.board.system.member.domain.model.fixture.usernameFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("MemberStorageFixture(Appender, Finder) 테스트")
class MemberStorageFixtureTest {


    private lateinit var memberStorageFixture: MemberStorageFixture

    @BeforeEach
    fun init() {
        memberStorageFixture = MemberStorageFixture()
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
            val savedMember = memberStorageFixture.save(member)

            // then
            assertThat(savedMember.id).isNotNull
            assertThat(savedMember.email).isEqualTo(member.email)
            assertThat(savedMember.password.value).isEqualTo(member.password.value)
            assertThat(savedMember.username).isEqualTo(member.username)
            assertThat(savedMember.nickname).isEqualTo(member.nickname)
            assertThat(savedMember.role).isEqualTo(member.role)
            assertThat(savedMember.registeredAt).isEqualTo(member.registeredAt)
        }

        @DisplayName("id 가 있는 회원을 저장하고 조회하면 기존 회원 정보를 덮어쓴 채 조회된다.")
        @Test
        fun test2() {
            // given
            val savedMember = memberStorageFixture.save(
                memberFixture(
                    password = "1111",
                )
            )

            val changedMember = memberStorageFixture.save(
                memberFixture(
                    id = savedMember.id,
                    password = "2222",
                )
            )

            // when
            val findMember = memberStorageFixture.findByIdOrNull(savedMember.id)!!

            // then
            assertThat(findMember.id).isEqualTo(savedMember.id)
            assertThat(findMember.id).isEqualTo(changedMember.id)
            assertThat(findMember.password.value).isEqualTo(changedMember.password.value)
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
            val savedMember = memberStorageFixture.save(memberFixture())

            // when
            val findMember = memberStorageFixture.findByIdOrNull(savedMember.id)!!


            // then
            assertThat(findMember).isNotNull
            assertThat(findMember.email).isEqualTo(member.email)
            assertThat(findMember.username).isEqualTo(member.username)
            assertThat(findMember.nickname).isEqualTo(member.nickname)
            assertThat(findMember.password.value).isEqualTo(member.password.value)
            assertThat(findMember.role).isEqualTo(member.role)
            assertThat(findMember.registeredAt).isEqualTo(member.registeredAt)
        }

        @DisplayName("존재하지 않는 id로 조회하면 null이 조회됨")
        @Test
        fun test2() {
            // given
            val memberId = 1557L

            // when
            val findMember = memberStorageFixture.findByIdOrNull(memberId)

            // then
            assertThat(findMember).isNull()
        }
    }


    @Nested
    @DisplayName("findByEmailOrNull: 이메일로 회원을 조회하여 반환한다.")
    inner class FindByEmailOrNullTest {

        @DisplayName("가입된 회원의 이메일로 조회하면 회원이 조회됨")
        @Test
        fun test1() {
            // given
            val member = memberFixture(email = "test1557@gmail.com")
            val savedMember = memberStorageFixture.save(member)

            // when
            val findMember = memberStorageFixture.findByEmailOrNull(savedMember.email)!!

            // then
            assertThat(findMember).isNotNull
            assertThat(findMember.email).isEqualTo(member.email)
            assertThat(findMember.username).isEqualTo(member.username)
            assertThat(findMember.nickname).isEqualTo(member.nickname)
            assertThat(findMember.password.value).isEqualTo(member.password.value)
            assertThat(findMember.role).isEqualTo(member.role)
            assertThat(findMember.registeredAt).isEqualTo(member.registeredAt)
        }

        @DisplayName("존재하지 않는 이메일로 조회하면 null이 조회됨")
        @Test
        fun test2() {
            // given
            val email = "ttascat@gmail.com"

            // when
            val findMember = memberStorageFixture.findByEmailOrNull(email)

            // then
            assertThat(findMember).isNull()
        }
    }

    @Nested
    @DisplayName("findByNicknameOrNull: 닉네임으로 회원을 조회하여 반환한다.")
    inner class FindByNicknameImplOrNullTest {

        @DisplayName("가입된 닉네임으로 조회하면 회원이 조회됨")
        @Test
        fun test1() {
            // given
            val member = memberFixture()
            val savedMember = memberStorageFixture.save(member)

            // when
            val findMember = memberStorageFixture.findByNicknameOrNull(savedMember.nickname)!!

            // then
            assertThat(findMember).isNotNull
            assertThat(findMember.email).isEqualTo(member.email)
            assertThat(findMember.username).isEqualTo(member.username)
            assertThat(findMember.nickname).isEqualTo(member.nickname)
            assertThat(findMember.password.value).isEqualTo(member.password.value)
            assertThat(findMember.role).isEqualTo(member.role)
            assertThat(findMember.registeredAt).isEqualTo(member.registeredAt)
        }

        @DisplayName("없는 닉네임으로 조회하면 null이 조회됨")
        @Test
        fun test2() {
            // given
            val nickname = nicknameFixture("마늘오리")

            // when
            val findMember = memberStorageFixture.findByNicknameOrNull(nickname)

            // then
            assertThat(findMember).isNull()
        }
    }

    @Nested
    @DisplayName("findByUsernameOrNull: 사용자 아이디(username)으로 회원을 조회하여 반환한다.")
    inner class FindByUsernameOrNullTest {


        @DisplayName("가입된 Username으로 조회하면 회원이 조회됨")
        @Test
        fun test1() {
            // given
            val member = memberFixture()
            val savedMember = memberStorageFixture.save(member)

            // when
            val findMember = memberStorageFixture.findByUsernameOrNull(savedMember.username)!!

            // then
            assertThat(findMember).isNotNull
            assertThat(findMember.email).isEqualTo(member.email)
            assertThat(findMember.username).isEqualTo(member.username)
            assertThat(findMember.nickname).isEqualTo(member.nickname)
            assertThat(findMember.password.value).isEqualTo(member.password.value)
            assertThat(findMember.role).isEqualTo(member.role)
            assertThat(findMember.registeredAt).isEqualTo(member.registeredAt)
        }

        @DisplayName("없는 사용자 아이디(username)으로 조회하면 null이 조회됨")
        @Test
        fun test2() {
            // given
            val username = usernameFixture("abcd1234")

            // when
            val findMember = memberStorageFixture.findByUsernameOrNull(username)

            // then
            assertThat(findMember).isNull()
        }
    }

    @Nested
    @DisplayName("existsById: 회원의 존재 여부를 반환한다.")
    inner class ExistsByIdTest {

        @DisplayName("id에 해당하는 회원이 존재하면 true가 반환됨")
        @Test
        fun test1() {
            // given
            val member = memberFixture()
            val savedMember = memberStorageFixture.save(member)

            // when
            val isExist = memberStorageFixture.existsById(savedMember.id)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("id에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val memberId = 1557L
            // when
            val isExist = memberStorageFixture.existsById(memberId)

            // then
            assertThat(isExist).isFalse()
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
            val savedMember = memberStorageFixture.save(member)

            // when
            val isExist = memberStorageFixture.existsByEmail(savedMember.email)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("이메일에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val email = "abcd@gmail.com"

            // when
            val isExist = memberStorageFixture.existsByEmail(email)

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
            val savedMember = memberStorageFixture.save(member)

            // when
            val isExist = memberStorageFixture.existsByNickname(savedMember.nickname)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("닉네임에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val nickname = nicknameFixture("페이커")
            // when
            val isExist = memberStorageFixture.existsByNickname(nickname)

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
            val savedMember = memberStorageFixture.save(member)

            // when
            val isExist = memberStorageFixture.existsByUsername(savedMember.username)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("Username 에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val username = usernameFixture("abcd124")
            // when
            val isExist = memberStorageFixture.existsByUsername(username)

            // then
            assertThat(isExist).isFalse()
        }
    }
}
