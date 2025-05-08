package com.ttasjwi.board.system.user.domain.mapper

import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.SocialLoginRequest
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import jakarta.xml.bind.ValidationException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SocialLoginCommandMapperTest {

    private lateinit var socialLoginCommandMapper: SocialLoginCommandMapper
    private lateinit var currentTime: AppDateTime

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        socialLoginCommandMapper = container.socialLoginCommandMapper
        currentTime = appDateTimeFixture(minute = 3)
        container.timeManagerFixture.changeCurrentTime(currentTime)
    }

    @Test
    @DisplayName("성공 테스트")
    fun test1() {
        // given
        val request = SocialLoginRequest(
            state = "state",
            code = "code"
        )

        // when
        val command = socialLoginCommandMapper.mapToCommand(request)

        // then
        assertThat(command.state).isEqualTo(request.state)
        assertThat(command.code).isEqualTo(request.code)
        assertThat(command.currentTime).isEqualTo(currentTime)
    }


    @Test
    @DisplayName("state 가 null 이면 예외 발생")
    fun test2() {
        // given
        val request = SocialLoginRequest(
            state = null,
            code = "code"
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            socialLoginCommandMapper.mapToCommand(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()

        assertThat(exceptions).hasSize(1)
        assertThat(exceptions[0]).isInstanceOf(NullArgumentException::class.java)
        assertThat(exceptions[0].source).isEqualTo("state")
    }

    @Test
    @DisplayName("code 가 null 이면 예외 발생")
    fun test3() {
        // given
        val request = SocialLoginRequest(
            state = "state",
            code = null
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            socialLoginCommandMapper.mapToCommand(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()

        assertThat(exceptions).hasSize(1)
        assertThat(exceptions[0]).isInstanceOf(NullArgumentException::class.java)
        assertThat(exceptions[0].source).isEqualTo("code")
    }
}
