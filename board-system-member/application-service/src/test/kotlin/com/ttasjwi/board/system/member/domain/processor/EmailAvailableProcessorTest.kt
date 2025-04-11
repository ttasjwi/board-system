package com.ttasjwi.board.system.member.domain.processor

import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.domain.dto.EmailAvailableQuery
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import com.ttasjwi.board.system.member.domain.port.fixture.EmailFormatValidatePortFixture
import com.ttasjwi.board.system.member.domain.port.fixture.MemberPersistencePortFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("EmailAvailableProcessor: 이메일이 사용가능한 지 여부를 실질적으로 확인하는 처리자")
class EmailAvailableProcessorTest {

    private lateinit var processor: EmailAvailableProcessor
    private lateinit var savedMember: Member

    @BeforeEach
    fun setup() {
        val memberPersistencePortFixture = MemberPersistencePortFixture()
        processor = EmailAvailableProcessor(
            emailFormatValidatePort = EmailFormatValidatePortFixture(),
            memberPersistencePort = memberPersistencePortFixture,
            messageResolver = MessageResolverFixture(),
        )
        savedMember = memberPersistencePortFixture.save(
            memberFixture(
                memberId = 123456L,
                email = "registered@gmail.com"
            )
        )
    }

    @Nested
    @DisplayName("checkEmailAvailable: 이메일이 사용가능한 지 여부를 확인하고 그 결과를 반환한다.")
    inner class CheckEmailAvailable {

        @Test
        @DisplayName("이메일 포맷이 유효하지 않을 때, 이메일 포맷이 유효하지 않다는 결과를 반환한다.")
        fun testInvalidFormat() {
            val query = EmailAvailableQuery(
                email = EmailFormatValidatePortFixture.ERROR_EMAIL,
                locale = Locale.KOREAN,
            )

            val result = processor.checkEmailAvailable(query)

            assertThat(result.yourEmail).isEqualTo(query.email)
            assertThat(result.isAvailable).isFalse()
            assertThat(result.reasonCode).isEqualTo("EmailAvailableCheck.InvalidFormat")
            assertThat(result.reasonMessage).isEqualTo("EmailAvailableCheck.InvalidFormat.message(locale=${query.locale},args=[])")
            assertThat(result.reasonDescription).isEqualTo("EmailAvailableCheck.InvalidFormat.description(locale=${query.locale},args=[])")
        }

        @Test
        @DisplayName("포맷이 올바르지만 이미 사용중인 이메일이면, 이미 사용 중이라는 결과를 반환한다.")
        fun testTaken() {
            val query = EmailAvailableQuery(
                email = savedMember.email,
                locale = Locale.ENGLISH
            )

            val result = processor.checkEmailAvailable(query)

            assertThat(result.yourEmail).isEqualTo(query.email)
            assertThat(result.isAvailable).isFalse()
            assertThat(result.reasonCode).isEqualTo("EmailAvailableCheck.Taken")
            assertThat(result.reasonMessage).isEqualTo("EmailAvailableCheck.Taken.message(locale=${query.locale},args=[])")
            assertThat(result.reasonDescription).isEqualTo("EmailAvailableCheck.Taken.description(locale=${query.locale},args=[])")
        }

        @Test
        @DisplayName("포맷이 올바르고, 사용 중이지 않다면, 사용 가능하다는 결과를 반환한다.")
        fun testAvailable() {
            val query = EmailAvailableQuery(
                email = "available@gmail.com",
                locale = Locale.ENGLISH
            )

            val result = processor.checkEmailAvailable(query)

            assertThat(result.yourEmail).isEqualTo(query.email)
            assertThat(result.isAvailable).isTrue()
            assertThat(result.reasonCode).isEqualTo("EmailAvailableCheck.Available")
            assertThat(result.reasonMessage).isEqualTo("EmailAvailableCheck.Available.message(locale=${query.locale},args=[])")
            assertThat(result.reasonDescription).isEqualTo("EmailAvailableCheck.Available.description(locale=${query.locale},args=[])")
        }
    }
}
