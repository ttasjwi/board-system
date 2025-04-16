package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.user.domain.dto.UsernameAvailableQuery
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.model.fixture.userFixture
import com.ttasjwi.board.system.user.domain.policy.fixture.UsernamePolicyFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("UsernameAvailableProcessor: 사용자아이디(username)가 사용가능한 지 여부를 실질적으로 확인하는 처리자")
class UsernameAvailableProcessorTest {

    private lateinit var processor: UsernameAvailableProcessor
    private lateinit var savedUser: User

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        val userPersistencePortFixture = container.userPersistencePortFixture
        processor = container.usernameAvailableProcessor
        savedUser = userPersistencePortFixture.save(
            userFixture(
                username = "notregistered"
            )
        )
    }

    @Nested
    @DisplayName("checkUsernameAvailable: Username이 사용가능한 지 여부를 확인하고 그 결과를 반환한다.")
    inner class CheckUsernameAvailable {

        @Test
        @DisplayName("Username 포맷이 유효하지 않을 때, Username 포맷이 유효하지 않다는 결과를 반환한다.")
        fun testInvalidFormat() {
            val query = UsernameAvailableQuery(
                username = UsernamePolicyFixture.ERROR_USERNAME,
                locale = Locale.ENGLISH
            )

            val result = processor.checkUsernameAvailable(query)

            assertThat(result.yourUsername).isEqualTo(query.username)
            assertThat(result.isAvailable).isFalse()
            assertThat(result.reasonCode).isEqualTo("UsernameAvailableCheck.InvalidFormat")
            assertThat(result.reasonMessage).isEqualTo("UsernameAvailableCheck.InvalidFormat.message(locale=${query.locale},args=[])")
            assertThat(result.reasonDescription).isEqualTo("UsernameAvailableCheck.InvalidFormat.description(locale=${query.locale},args=[])")
        }

        @Test
        @DisplayName("포맷이 올바르지만 이미 사용중인 Username이면, 이미 사용 중이라는 결과를 반환한다.")
        fun testTaken() {
            val query = UsernameAvailableQuery(
                username = savedUser.username,
                locale = Locale.CHINESE
            )

            val result = processor.checkUsernameAvailable(query)

            assertThat(result.yourUsername).isEqualTo(query.username)
            assertThat(result.isAvailable).isFalse()
            assertThat(result.reasonCode).isEqualTo("UsernameAvailableCheck.Taken")
            assertThat(result.reasonMessage).isEqualTo("UsernameAvailableCheck.Taken.message(locale=${query.locale},args=[])")
            assertThat(result.reasonDescription).isEqualTo("UsernameAvailableCheck.Taken.description(locale=${query.locale},args=[])")
        }

        @Test
        @DisplayName("포맷이 올바르고, 사용 중이지 않다면, 사용 가능하다는 결과를 반환한다.")
        fun testAvailable() {
            val query = UsernameAvailableQuery(
                username = "available",
                locale = Locale.GERMANY
            )

            val result = processor.checkUsernameAvailable(query)

            assertThat(result.yourUsername).isEqualTo(query.username)
            assertThat(result.isAvailable).isTrue()
            assertThat(result.reasonCode).isEqualTo("UsernameAvailableCheck.Available")
            assertThat(result.reasonMessage).isEqualTo("UsernameAvailableCheck.Available.message(locale=${query.locale},args=[])")
            assertThat(result.reasonDescription).isEqualTo("UsernameAvailableCheck.Available.description(locale=${query.locale},args=[])")
        }
    }
}
