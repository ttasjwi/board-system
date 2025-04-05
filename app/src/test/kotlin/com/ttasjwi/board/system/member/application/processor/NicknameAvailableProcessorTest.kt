package com.ttasjwi.board.system.member.application.processor

import com.ttasjwi.board.system.global.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.global.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.application.dto.NicknameAvailableQuery
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import com.ttasjwi.board.system.member.domain.service.fixture.MemberStorageFixture
import com.ttasjwi.board.system.member.domain.service.fixture.NicknameManagerFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("NicknameAvailableProcessor: 닉네임이 사용가능한 지 여부를 실질적으로 확인하는 처리자")
class NicknameAvailableProcessorTest {

    private lateinit var processor: NicknameAvailableProcessor
    private lateinit var savedMember: Member

    @BeforeEach
    fun setup() {
        val memberStorageFixture = MemberStorageFixture()
        val nicknameManagerFixture = NicknameManagerFixture()
        processor = NicknameAvailableProcessor(
            nicknameManager = nicknameManagerFixture,
            memberFinder = memberStorageFixture,
            messageResolver = MessageResolverFixture(),
            localeManager = LocaleManagerFixture(),
        )
        savedMember = memberStorageFixture.save(
            memberFixture(
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
            val query = NicknameAvailableQuery(nickname = NicknameManagerFixture.ERROR_NICKNAME)

            val result = processor.checkNicknameAvailable(query)

            assertThat(result.yourNickname).isEqualTo(query.nickname)
            assertThat(result.isAvailable).isFalse()
            assertThat(result.reasonCode).isEqualTo("NicknameAvailableCheck.InvalidFormat")
            assertThat(result.reasonMessage).isEqualTo("NicknameAvailableCheck.InvalidFormat.message(locale=${Locale.KOREAN},args=[])")
            assertThat(result.reasonDescription).isEqualTo("NicknameAvailableCheck.InvalidFormat.description(locale=${Locale.KOREAN},args=[])")
        }

        @Test
        @DisplayName("포맷이 올바르지만 이미 사용중인 닉네임이면, 이미 사용 중이라는 결과를 반환한다.")
        fun testTaken() {
            val query = NicknameAvailableQuery(nickname = savedMember.nickname)

            val result = processor.checkNicknameAvailable(query)

            assertThat(result.yourNickname).isEqualTo(query.nickname)
            assertThat(result.isAvailable).isFalse()
            assertThat(result.reasonCode).isEqualTo("NicknameAvailableCheck.Taken")
            assertThat(result.reasonMessage).isEqualTo("NicknameAvailableCheck.Taken.message(locale=${Locale.KOREAN},args=[])")
            assertThat(result.reasonDescription).isEqualTo("NicknameAvailableCheck.Taken.description(locale=${Locale.KOREAN},args=[])")
        }

        @Test
        @DisplayName("포맷이 올바르고, 사용 중이지 않다면, 사용 가능하다는 결과를 반환한다.")
        fun testAvailable() {
            val query = NicknameAvailableQuery(nickname = "available")

            val result = processor.checkNicknameAvailable(query)

            assertThat(result.yourNickname).isEqualTo(query.nickname)
            assertThat(result.isAvailable).isTrue()
            assertThat(result.reasonCode).isEqualTo("NicknameAvailableCheck.Available")
            assertThat(result.reasonMessage).isEqualTo("NicknameAvailableCheck.Available.message(locale=${Locale.KOREAN},args=[])")
            assertThat(result.reasonDescription).isEqualTo("NicknameAvailableCheck.Available.description(locale=${Locale.KOREAN},args=[])")
        }
    }
}
