package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.user.domain.model.fixture.userFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("UserStorageFixture(Appender, Finder) 테스트")
class UserPersistencePortFixtureTest {

    private lateinit var userPersistencePortFixture: UserPersistencePortFixture

    @BeforeEach
    fun init() {
        userPersistencePortFixture = UserPersistencePortFixture()
    }

    @Nested
    @DisplayName("save: 회원을 저장하고, id 를 발급받아 반환시킨다.")
    inner class SaveTest {

        @DisplayName("id가 없는 회원을 저장하면 id가 발급된다.")
        @Test
        fun test1() {
            // given
            val user = userFixture()

            // when
            val savedUser = userPersistencePortFixture.save(user)

            // then
            assertThat(savedUser.userId).isNotNull
            assertThat(savedUser.email).isEqualTo(user.email)
            assertThat(savedUser.password).isEqualTo(user.password)
            assertThat(savedUser.username).isEqualTo(user.username)
            assertThat(savedUser.nickname).isEqualTo(user.nickname)
            assertThat(savedUser.role).isEqualTo(user.role)
            assertThat(savedUser.registeredAt).isEqualTo(user.registeredAt)
        }

        @DisplayName("id 가 있는 회원을 저장하고 조회하면 기존 회원 정보를 덮어쓴 채 조회된다.")
        @Test
        fun test2() {
            // given
            val savedUser = userPersistencePortFixture.save(
                userFixture(
                    password = "1111",
                )
            )

            val changedUser = userPersistencePortFixture.save(
                userFixture(
                    userId = savedUser.userId,
                    password = "2222",
                )
            )

            // when
            val findUser = userPersistencePortFixture.findByIdOrNull(savedUser.userId)!!

            // then
            assertThat(findUser.userId).isEqualTo(savedUser.userId)
            assertThat(findUser.userId).isEqualTo(changedUser.userId)
            assertThat(findUser.password).isEqualTo(changedUser.password)
        }

    }

    @Nested
    @DisplayName("findByIdOrNull: id 로 회원을 조회한다.")
    inner class FindByIdOrNullTest {

        @DisplayName("존재하는 회원 id로 조회하면 해당 회원이 조회됨")
        @Test
        fun test1() {
            // given
            val user = userFixture()
            val savedUser = userPersistencePortFixture.save(userFixture())

            // when
            val findUser = userPersistencePortFixture.findByIdOrNull(savedUser.userId)!!


            // then
            assertThat(findUser).isNotNull
            assertThat(findUser.email).isEqualTo(user.email)
            assertThat(findUser.username).isEqualTo(user.username)
            assertThat(findUser.nickname).isEqualTo(user.nickname)
            assertThat(findUser.password).isEqualTo(user.password)
            assertThat(findUser.role).isEqualTo(user.role)
            assertThat(findUser.registeredAt).isEqualTo(user.registeredAt)
        }

        @DisplayName("존재하지 않는 id로 조회하면 null이 조회됨")
        @Test
        fun test2() {
            // given
            val userId = 1557L

            // when
            val findUser = userPersistencePortFixture.findByIdOrNull(userId)

            // then
            assertThat(findUser).isNull()
        }
    }

    @Nested
    @DisplayName("findAuthUserOrNull : 회원을 인증회원 형태로 복원해 조회한다.")
    inner class FindAuthUserOrNullTest {


        @Test
        @DisplayName("회원이 있으면 authUser 형태로 조회된다.")
        fun success() {
            val userId = 149L
            val role = Role.ROOT
            val user = userFixture(userId = userId, role = role)
            userPersistencePortFixture.save(user)

            val authUser = userPersistencePortFixture.findAuthUserOrNull(userId)!!

            assertThat(authUser.userId).isEqualTo(user.userId)
            assertThat(authUser.role).isEqualTo(role)
        }

        @Test
        @DisplayName("회원이 없으면 null 이 반환된다.")
        fun nullTest() {
            val userId = 121356L
            val authUser = userPersistencePortFixture.findAuthUserOrNull(userId)
            assertThat(authUser).isNull()
        }
    }

    @Nested
    @DisplayName("findByEmailOrNull : 회원을 이메일로 조회")
    inner class FindByEmailOrNullTest {


        @Test
        @DisplayName("이메일에 해당하는 회원이 있을 경우 반환된다.")
        fun successTest() {
            // given
            val user = userFixture(userId = 233L, email = "jest@gmail.com")
            val savedUser = userPersistencePortFixture.save(user)

            // when
            val findUser = userPersistencePortFixture.findByEmailOrNull(savedUser.email)!!

            // then
            assertThat(findUser).isNotNull
            assertThat(findUser.userId).isEqualTo(savedUser.userId)
            assertThat(findUser.email).isEqualTo(savedUser.email)
        }


        @Test
        @DisplayName("이메일에 해당하는 회원이 없으면 Null 이 반환된다.")
        fun failureTest() {
            // given
            val email = "abcd@gmail.com"

            // when
            val findUser = userPersistencePortFixture.findByEmailOrNull(email)

            // then
            assertThat(findUser).isNull()
        }
    }


    @Nested
    @DisplayName("existsByEmail: 이메일로 회원의 존재여부를 반환한다.")
    inner class ExistsByEmailTest {
        @DisplayName("id에 해당하는 회원이 존재하면 true가 반환됨")
        @Test
        fun test1() {
            // given
            val user = userFixture()
            val savedUser = userPersistencePortFixture.save(user)

            // when
            val isExist = userPersistencePortFixture.existsByEmail(savedUser.email)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("이메일에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val email = "abcd@gmail.com"

            // when
            val isExist = userPersistencePortFixture.existsByEmail(email)

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
            val user = userFixture()
            val savedUser = userPersistencePortFixture.save(user)

            // when
            val isExist = userPersistencePortFixture.existsByNickname(savedUser.nickname)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("닉네임에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val nickname = "페이커"

            // when
            val isExist = userPersistencePortFixture.existsByNickname(nickname)

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
            val user = userFixture()
            val savedUser = userPersistencePortFixture.save(user)

            // when
            val isExist = userPersistencePortFixture.existsByUsername(savedUser.username)

            // then
            assertThat(isExist).isTrue()
        }

        @DisplayName("Username 에 해당하는 회원이 없다면 false가 반환됨")
        @Test
        fun test2() {
            // given
            val username = "abcd124"
            // when
            val isExist = userPersistencePortFixture.existsByUsername(username)

            // then
            assertThat(isExist).isFalse()
        }
    }
}
