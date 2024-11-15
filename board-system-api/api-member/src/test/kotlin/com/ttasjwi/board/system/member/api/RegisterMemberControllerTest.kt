package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import com.ttasjwi.board.system.core.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.core.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.application.usecase.fixture.RegisterMemberUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

@DisplayName("RegisterMemberController 테스트")
class RegisterMemberControllerTest {

    private lateinit var controller: RegisterMemberController
    private lateinit var useCaseFixture: RegisterMemberUseCaseFixture
    private lateinit var messageResolverFixture: MessageResolverFixture
    private lateinit var localeManagerFixture: LocaleManagerFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = RegisterMemberUseCaseFixture()
        messageResolverFixture = MessageResolverFixture()
        localeManagerFixture = LocaleManagerFixture()
        controller = RegisterMemberController(
            useCase = useCaseFixture,
            messageResolver = messageResolverFixture,
            localeManager = localeManagerFixture
        )
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 그 결과를 기반으로 200 코드와 함께 응답을 반환한다.")
    fun test() {
        // given
        val request = RegisterMemberRequest(
            email = "test@test.com",
            password = "1111",
            username = "testuser",
            nickname = "testnick"
        )

        // when
        val responseEntity = controller.register(request)
        val response = responseEntity.body as SuccessResponse<RegisterMemberResponse>

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.isSuccess).isTrue()
        assertThat(response.code).isEqualTo("RegisterMember.Complete")
        assertThat(response.message).isEqualTo("RegisterMember.Complete.message(locale=${Locale.KOREAN},args=[])")
        assertThat(response.description).isEqualTo("RegisterMember.Complete.description(locale=${Locale.KOREAN},args=[])")

        val registeredMember = response.data.registeredMember

        assertThat(registeredMember.memberId).isNotNull()
        assertThat(registeredMember.email).isEqualTo(request.email)
        assertThat(registeredMember.username).isEqualTo(request.username)
        assertThat(registeredMember.nickname).isEqualTo(request.nickname)
        assertThat(registeredMember.role).isNotNull()
        assertThat(registeredMember.registeredAt).isNotNull()
    }
}
