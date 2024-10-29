package com.ttasjwi.board.system.member.application.processor

import com.ttasjwi.board.system.member.application.dto.EmailAvailableQuery
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixtureNotRegistered
import com.ttasjwi.board.system.member.domain.service.fixture.EmailCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.MemberStorageFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("EmailAvailableProcessor: 이메일이 사용가능한 지 여부를 실질적으로 확인하는 처리자")
class EmailAvailableProcessorTest {

    private lateinit var processor: EmailAvailableProcessor
    private lateinit var savedMember: Member

    @BeforeEach
    fun setup() {
        val memberStorageFixture = MemberStorageFixture()
        val emailCreatorFixture = EmailCreatorFixture()
        processor = EmailAvailableProcessor(
            emailCreator = emailCreatorFixture,
            memberFinder = memberStorageFixture
        )
        savedMember = memberStorageFixture.save(
            memberFixtureNotRegistered(
                email = "registered@gmail.com"
            )
        )
    }

    @Nested
    @DisplayName("queryEmailAvailable: 이메일이 사용가능한 지 여부를 확인하고 그 결과를 반환한다.")
    inner class QueryEmailAvailable {

        @Test
        @DisplayName("이메일 포맷이 유효하지 않을 때, 이메일 포맷이 유효하지 않다는 결과를 반환한다.")
        fun testInvalidFormat() {
            val query = EmailAvailableQuery(email = EmailCreatorFixture.ERROR_EMAIL)

            val result = processor.checkEmailAvailable(query)

            assertThat(result.email).isEqualTo(query.email)
            assertThat(result.isAvailable).isFalse()
            assertThat(result.reasonCode).isEqualTo("EmailAvailableCheck.InvalidFormat")
        }

        @Test
        @DisplayName("포맷이 올바르지만 이미 사용중인 이메일이면, 이미 사용 중이라는 결과를 반환한다.")
        fun testTaken() {
            val query = EmailAvailableQuery(email = savedMember.email.value)

            val result = processor.checkEmailAvailable(query)

            assertThat(result.email).isEqualTo(query.email)
            assertThat(result.isAvailable).isFalse()
            assertThat(result.reasonCode).isEqualTo("EmailAvailableCheck.Taken")
        }

        @Test
        @DisplayName("포맷이 올바르고, 사용 중이지 않다면, 사용 가능하다는 결과를 반환한다.")
        fun testAvailable() {
            val query = EmailAvailableQuery(email = "available@gmail.com")

            val result = processor.checkEmailAvailable(query)

            assertThat(result.email).isEqualTo(query.email)
            assertThat(result.isAvailable).isTrue()
            assertThat(result.reasonCode).isEqualTo("EmailAvailableCheck.Available")
        }
    }
}
