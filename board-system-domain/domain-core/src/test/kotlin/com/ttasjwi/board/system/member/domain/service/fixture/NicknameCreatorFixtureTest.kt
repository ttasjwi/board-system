package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.core.exception.CustomException
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*


@DisplayName("NicknameCreatorFixture: 닉네임 생성기 픽스쳐")
class NicknameCreatorFixtureTest {

    private lateinit var nicknameCreatorFixture: NicknameCreatorFixture

    @BeforeEach
    fun setup() {
        this.nicknameCreatorFixture = NicknameCreatorFixture()
    }

    @Nested
    @DisplayName("create : 문자열로부터 Nickname 인스턴스를 생성하고 그 결과를 Result 로 담아 반환한다.")
    inner class Create {

        @Test
        @DisplayName("성공하면 그 값을 가진 Nickname 을 Result 에 담아 반환한다.")
        fun testSuccess() {
            val value = "땃쥐"

            val result = nicknameCreatorFixture.create(value)
            val nickname = result.getOrThrow()

            assertThat(result.isSuccess).isTrue()
            assertThat(nickname.value).isEqualTo(value)
        }

        @Test
        @DisplayName("ERROR_NICKNAME 을 전달하면 실패한다.")
        fun testFailure() {
            val value = NicknameCreatorFixture.ERROR_NICKNAME

            val result = nicknameCreatorFixture.create(value)
            val exception = assertThrows<CustomException> { result.getOrThrow() }


            assertThat(result.isFailure).isTrue()
            assertThat(exception.debugMessage).isEqualTo("닉네임 포맷 예외 - 픽스쳐")
        }
    }
}
