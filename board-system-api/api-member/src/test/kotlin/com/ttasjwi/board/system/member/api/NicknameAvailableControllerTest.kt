package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import com.ttasjwi.board.system.core.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.core.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.fixture.NicknameAvailableUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

@DisplayName("NicknameAvailableController 테스트")
class NicknameAvailableControllerTest {

    private lateinit var controller: NicknameAvailableController
    private lateinit var useCaseFixture: NicknameAvailableUseCaseFixture
    private lateinit var messageResolverFixture: MessageResolverFixture
    private lateinit var localeManagerFixture: LocaleManagerFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = NicknameAvailableUseCaseFixture()
        messageResolverFixture = MessageResolverFixture()
        localeManagerFixture = LocaleManagerFixture()
        controller = NicknameAvailableController(useCaseFixture, messageResolverFixture, localeManagerFixture)
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 그 결과를 기반으로 200 코드와 함께 응답을 반환한다.")
    fun test() {
        // given
        val request = NicknameAvailableRequest(nickname = "hello")

        // when
        val responseEntity = controller.checkNicknameAvailable(request)
        val response = responseEntity.body as SuccessResponse<NicknameAvailableResponse>

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.isSuccess).isTrue()
        assertThat(response.code).isEqualTo("NicknameAvailableCheck.Complete")
        assertThat(response.message).isEqualTo("NicknameAvailableCheck.Complete.message(locale=${Locale.KOREAN})")
        assertThat(response.description).isEqualTo("NicknameAvailableCheck.Complete.description(args=[$.data.nicknameAvailable],locale=${Locale.KOREAN})")

        val nicknameAvailable = response.data.nicknameAvailable

        assertThat(nicknameAvailable.nickname).isEqualTo(request.nickname)
        assertThat(nicknameAvailable.isAvailable).isEqualTo(true)
        assertThat(nicknameAvailable.reasonCode).isEqualTo("NicknameAvailableCheck.Available")
        assertThat(nicknameAvailable.message).isEqualTo("NicknameAvailableCheck.Available.message(locale=${Locale.KOREAN})")
        assertThat(nicknameAvailable.description).isEqualTo("NicknameAvailableCheck.Available.description(args=[],locale=${Locale.KOREAN})")
    }
}
