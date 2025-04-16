package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.user.domain.dto.NicknameAvailableQuery
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.model.fixture.userFixture
import com.ttasjwi.board.system.user.domain.policy.fixture.NicknamePolicyFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("NicknameAvailableProcessor: 닉네임이 사용가능한 지 여부를 실질적으로 확인하는 처리자")
class NicknameAvailableProcessorTest {

    private lateinit var processor: NicknameAvailableProcessor
    private lateinit var savedUser: User

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        val userPersistencePortFixture = container.userPersistencePortFixture
        processor = container.nicknameAvailableProcessor
        savedUser = userPersistencePortFixture.save(
            userFixture(
                nickname = "saved"
            )
        )
    }

    @Nested
    @DisplayName("checkNicknameAvailable: 닉네임이 사용가능한 지 여부를 확인하고 그 결과를 반환한다.")
    inner class CheckNicknameAvailable {

        @Test
        @DisplayName("닉네임 포맷이 유효하지 않을 때, 닉네임 포맷이 유효하지 않다는 결과를 반환한다.")
        fun testInvalidFormat() {
            val query = NicknameAvailableQuery(
                nickname = NicknamePolicyFixture.ERROR_NICKNAME,
                locale = Locale.FRANCE
            )

            val result = processor.checkNicknameAvailable(query)

            assertThat(result.yourNickname).isEqualTo(query.nickname)
            assertThat(result.isAvailable).isFalse()
            assertThat(result.reasonCode).isEqualTo("NicknameAvailableCheck.InvalidFormat")
            assertThat(result.reasonMessage).isEqualTo("NicknameAvailableCheck.InvalidFormat.message(locale=${query.locale},args=[])")
            assertThat(result.reasonDescription).isEqualTo("NicknameAvailableCheck.InvalidFormat.description(locale=${query.locale},args=[])")
        }

        @Test
        @DisplayName("포맷이 올바르지만 이미 사용중인 닉네임이면, 이미 사용 중이라는 결과를 반환한다.")
        fun testTaken() {
            val query = NicknameAvailableQuery(
                nickname = savedUser.nickname,
                locale = Locale.CHINESE
            )

            val result = processor.checkNicknameAvailable(query)

            assertThat(result.yourNickname).isEqualTo(query.nickname)
            assertThat(result.isAvailable).isFalse()
            assertThat(result.reasonCode).isEqualTo("NicknameAvailableCheck.Taken")
            assertThat(result.reasonMessage).isEqualTo("NicknameAvailableCheck.Taken.message(locale=${query.locale},args=[])")
            assertThat(result.reasonDescription).isEqualTo("NicknameAvailableCheck.Taken.description(locale=${query.locale},args=[])")
        }

        @Test
        @DisplayName("포맷이 올바르고, 사용 중이지 않다면, 사용 가능하다는 결과를 반환한다.")
        fun testAvailable() {
            val query = NicknameAvailableQuery(
                nickname = "available",
                locale = Locale.JAPAN
            )

            val result = processor.checkNicknameAvailable(query)

            assertThat(result.yourNickname).isEqualTo(query.nickname)
            assertThat(result.isAvailable).isTrue()
            assertThat(result.reasonCode).isEqualTo("NicknameAvailableCheck.Available")
            assertThat(result.reasonMessage).isEqualTo("NicknameAvailableCheck.Available.message(locale=${query.locale},args=[])")
            assertThat(result.reasonDescription).isEqualTo("NicknameAvailableCheck.Available.description(locale=${query.locale},args=[])")
        }
    }
}
