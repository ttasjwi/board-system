package com.ttasjwi.board.system.domain.fixture

import com.ttasjwi.board.system.user.domain.EmailVerificationStartRequest
import com.ttasjwi.board.system.user.domain.fixture.EmailVerificationStartUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationStartUseCase 픽스쳐 테스트")
class EmailVerificationStartUseCaseFixtureTest {

    private lateinit var useCaseFixture: EmailVerificationStartUseCaseFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = EmailVerificationStartUseCaseFixture()
    }

    @Test
    @DisplayName("요청 email 값과 동일한 email, 그리고 임의로 지정한 코드 만료시간을 반환한다")
    fun test() {
        val request = EmailVerificationStartRequest(
            email = "hello@gmail.com",
        )
        val result = useCaseFixture.startEmailVerification(request)

        assertThat(result.email).isEqualTo(request.email)
        assertThat(result.codeExpiresAt).isNotNull
    }
}
