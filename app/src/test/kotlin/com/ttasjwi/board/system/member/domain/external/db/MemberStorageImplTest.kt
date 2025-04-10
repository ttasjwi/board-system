package com.ttasjwi.board.system.member.domain.external.db

import com.ttasjwi.board.system.IntegrationTest
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional

@Transactional
@DisplayName("MemberStorageImpl 테스트")
class MemberStorageImplTest : IntegrationTest() {

    @Nested
    @DisplayName("save: 회원을 저장하고, id 를 발급받아 반환시킨다.")
    inner class SaveTest {

        @DisplayName("id가 없는 회원을 저장하면 id가 발급된다.")
        @Test
        fun test1() {
            // given
            val member = memberFixture()

            // when
            val savedMember = memberStorageImpl.save(member)

            // then
            assertThat(savedMember.id).isNotNull
            assertThat(savedMember.email).isEqualTo(member.email)
            assertThat(savedMember.password).isEqualTo(member.password)
            assertThat(savedMember.username).isEqualTo(member.username)
            assertThat(savedMember.nickname).isEqualTo(member.nickname)
            assertThat(savedMember.role).isEqualTo(member.role)
            assertThat(savedMember.registeredAt).isEqualTo(member.registeredAt)
        }

        @DisplayName("id 가 있는 회원을 저장하고 조회하면 기존 회원 정보를 덮어쓴 채 조회된다.")
        @Transactional
        @Test
        fun test2() {
            // given
            val savedMember = memberStorageImpl.save(
                memberFixture(
                    id = 1L,
                    password = "1111",
                )
            )

            val changedMember = memberStorageImpl.save(
                memberFixture(
                    id = savedMember.id,
                    password = "2222",
                )
            )
            flushAndClearEntityManager()

            // when
            val findMember = memberStorageImpl.findByIdOrNull(savedMember.id)!!

            // then
            assertThat(findMember.id).isEqualTo(savedMember.id)
            assertThat(findMember.id).isEqualTo(changedMember.id)
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
            val savedMember = memberStorageImpl.save(memberFixture())
            flushAndClearEntityManager()

            // when
            val findMember = memberStorageImpl.findByIdOrNull(savedMember.id)!!

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
            val findMember = memberStorageImpl.findByIdOrNull(memberId)

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
            val savedMember = memberStorageImpl.save(member)
            flushAndClearEntityManager()

            // when
            val findMember = memberStorageImpl.findByEmailOrNull(savedMember.email)!!

            // then
            assertThat(findMember).isNotNull
            assertThat(findMember.email).isEqualTo(member.email)
            assertThat(findMember.username).isEqualTo(member.username)
            assertThat(findMember.nickname).isEqualTo(member.nickname)
            assertThat(findMember.password).isEqualTo(member.password)
            assertThat(findMember.role).isEqualTo(member.role)
            assertThat(findMember.registeredAt).isEqualTo(member.registeredAt)
        }

        @DisplayName("존재하지 않는 이메일로 조회하면 null이 조회됨")
        @Test
        fun test2() {
            // given
            val email = "ttascat@gmail.com"

            // when
            val findMember = memberStorageImpl.findByEmailOrNull(email)

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
            val savedMember = memberStorageImpl.save(member)
            flushAndClearEntityManager()

            // when
            val findMember = memberStorageImpl.findByNicknameOrNull(savedMember.nickname)!!

            // then
            assertThat(findMember).isNotNull
            assertThat(findMember.email).isEqualTo(member.email)
            assertThat(findMember.username).isEqualTo(member.username)
            assertThat(findMember.nickname).isEqualTo(member.nickname)
            assertThat(findMember.password).isEqualTo(member.password)
            assertThat(findMember.role).isEqualTo(member.role)
            assertThat(findMember.registeredAt).isEqualTo(member.registeredAt)
        }

        @DisplayName("없는 닉네임으로 조회하면 null이 조회됨")
        @Test
        fun test2() {
            // given
            val nickname = "마늘오리"

            // when
            val findMember = memberStorageImpl.findByNicknameOrNull(nickname)

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
            val savedMember = memberStorageImpl.save(member)
            flushAndClearEntityManager()

            // when
            val findMember = memberStorageImpl.findByUsernameOrNull(savedMember.username)!!

            // then
            assertThat(findMember).isNotNull
            assertThat(findMember.email).isEqualTo(member.email)
            assertThat(findMember.username).isEqualTo(member.username)
            assertThat(findMember.nickname).isEqualTo(member.nickname)
            assertThat(findMember.password).isEqualTo(member.password)
            assertThat(findMember.role).isEqualTo(member.role)
            assertThat(findMember.registeredAt).isEqualTo(member.registeredAt)
        }

        @DisplayName("없는 사용자 아이디(username)으로 조회하면 null이 조회됨")
        @Test
        fun test2() {
            // given
            val username = "abcd1234"

            // when
            val findMember = memberStorageImpl.findByUsernameOrNull(username)

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
            val savedMember = memberStorageImpl.save(member)
            flushAndClearEntityManager()

            // when
            val isExist = memberStorageImpl.existsById(savedMember.id!!)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("id에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val memberId = 1557L

            // when
            val isExist = memberStorageImpl.existsById(memberId)

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
            val savedMember = memberStorageImpl.save(member)
            flushAndClearEntityManager()

            // when
            val isExist = memberStorageImpl.existsByEmail(savedMember.email)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("이메일에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val email = "abcd@gmail.com"

            // when
            val isExist = memberStorageImpl.existsByEmail(email)

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
            val savedMember = memberStorageImpl.save(member)
            flushAndClearEntityManager()

            // when
            val isExist = memberStorageImpl.existsByNickname(savedMember.nickname)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("닉네임에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val nickname = "페이커"

            // when
            val isExist = memberStorageImpl.existsByNickname(nickname)

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
            val savedMember = memberStorageImpl.save(member)
            flushAndClearEntityManager()

            // when
            val isExist = memberStorageImpl.existsByUsername(savedMember.username)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("Username 에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val username = "abcd124"
            // when
            val isExist = memberStorageImpl.existsByUsername(username)

            // then
            assertThat(isExist).isFalse()
        }
    }
}
