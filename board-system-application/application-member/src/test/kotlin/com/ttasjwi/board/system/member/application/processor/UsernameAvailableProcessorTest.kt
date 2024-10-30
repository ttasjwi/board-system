package com.ttasjwi.board.system.member.application.processor

import com.ttasjwi.board.system.member.application.dto.UsernameAvailableQuery
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixtureNotRegistered
import com.ttasjwi.board.system.member.domain.service.fixture.MemberStorageFixture
import com.ttasjwi.board.system.member.domain.service.fixture.UsernameCreatorFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("UsernameAvailableProcessor: 사용자아이디(username)가 사용가능한 지 여부를 실질적으로 확인하는 처리자")
class UsernameAvailableProcessorTest {

    private lateinit var processor: UsernameAvailableProcessor
    private lateinit var savedMember: Member

    @BeforeEach
    fun setup() {
        val memberStorageFixture = MemberStorageFixture()
        val usernameCreatorFixture = UsernameCreatorFixture()
        processor = UsernameAvailableProcessor(
            usernameCreator = usernameCreatorFixture,
            memberFinder = memberStorageFixture
        )
        savedMember = memberStorageFixture.save(
            memberFixtureNotRegistered(
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
            val query = UsernameAvailableQuery(username = UsernameCreatorFixture.ERROR_USERNAME)

            val result = processor.checkUsernameAvailable(query)

            assertThat(result.username).isEqualTo(query.username)
            assertThat(result.isAvailable).isFalse()
            assertThat(result.reasonCode).isEqualTo("UsernameAvailableCheck.InvalidFormat")
        }

        @Test
        @DisplayName("포맷이 올바르지만 이미 사용중인 Username이면, 이미 사용 중이라는 결과를 반환한다.")
        fun testTaken() {
            val query = UsernameAvailableQuery(username = savedMember.username.value)

            val result = processor.checkUsernameAvailable(query)

            assertThat(result.username).isEqualTo(query.username)
            assertThat(result.isAvailable).isFalse()
            assertThat(result.reasonCode).isEqualTo("UsernameAvailableCheck.Taken")
        }

        @Test
        @DisplayName("포맷이 올바르고, 사용 중이지 않다면, 사용 가능하다는 결과를 반환한다.")
        fun testAvailable() {
            val query = UsernameAvailableQuery(username = "available")

            val result = processor.checkUsernameAvailable(query)

            assertThat(result.username).isEqualTo(query.username)
            assertThat(result.isAvailable).isTrue()
            assertThat(result.reasonCode).isEqualTo("UsernameAvailableCheck.Available")
        }
    }
}
