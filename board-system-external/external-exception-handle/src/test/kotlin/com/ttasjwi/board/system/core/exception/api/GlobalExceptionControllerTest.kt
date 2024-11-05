package com.ttasjwi.board.system.core.exception.api

import com.ttasjwi.board.system.core.api.ErrorResponse
import com.ttasjwi.board.system.core.exception.NullArgumentException
import com.ttasjwi.board.system.core.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.core.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.core.message.fixture.MessageResolverFixture
import jakarta.servlet.ServletException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*


@DisplayName("GlobalExceptionController 테스트")
class GlobalExceptionControllerTest {

    private lateinit var exceptionController: GlobalExceptionController
    private lateinit var messageResolverFixture: MessageResolverFixture
    private lateinit var localeManagerFixture: LocaleManagerFixture

    @BeforeEach
    fun setup() {
        messageResolverFixture = MessageResolverFixture()
        localeManagerFixture = LocaleManagerFixture()
        exceptionController = GlobalExceptionController(
            messageResolver = messageResolverFixture,
            localeManager = localeManagerFixture
        )
    }


    @Test
    @DisplayName("Exception 및 그 자손은 서버 에러로 취급한다.")
    fun handleExceptionTest() {
        val exception = Exception()

        val responseEntity = exceptionController.handleException(exception)
        val response = responseEntity.body as ErrorResponse

        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        assertThat(response.isSuccess).isFalse()
        assertThat(response.code).isEqualTo("Error.Occurred")
        assertThat(response.message).isEqualTo("Error.Occurred.message(locale=${Locale.KOREAN})")
        assertThat(response.description).isEqualTo("Error.Occurred.description(args=[],locale=${Locale.KOREAN})")
        assertThat(response.errors.size).isEqualTo(1)

        val errorItem = response.errors[0]
        assertThat(errorItem.code).isEqualTo("Error.Server")
        assertThat(errorItem.message).isEqualTo("Error.Server.message(locale=${Locale.KOREAN})")
        assertThat(errorItem.description).isEqualTo("Error.Server.description(args=[],locale=${Locale.KOREAN})")
        assertThat(errorItem.source).isEqualTo("server")
    }

    @Test
    @DisplayName("우리 서비스에서 정의한 CustomException 을 받으면 예외 정보를 기반으로 예외처리한다.")
    fun handleCustomException() {
        val exception = NullArgumentException("email")

        val responseEntity = exceptionController.handleCustomException(exception)
        val response = responseEntity.body as ErrorResponse

        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(response.isSuccess).isFalse()
        assertThat(response.code).isEqualTo("Error.Occurred")
        assertThat(response.message).isEqualTo("Error.Occurred.message(locale=${Locale.KOREAN})")
        assertThat(response.description).isEqualTo("Error.Occurred.description(args=[],locale=${Locale.KOREAN})")
        assertThat(response.errors.size).isEqualTo(1)

        val errorItem = response.errors[0]
        assertThat(errorItem.code).isEqualTo(exception.code)
        assertThat(errorItem.message).isEqualTo("${exception.code}.message(locale=${Locale.KOREAN})")
        assertThat(errorItem.description).isEqualTo("${exception.code}.description(args=${exception.args},locale=${Locale.KOREAN})")
        assertThat(errorItem.source).isEqualTo(exception.source)
    }

    @Test
    @DisplayName("NotImplementedError 처리 테스트")
    fun handleNotImplementedError() {
        val e = NotImplementedError()

        val servletException = ServletException(e)

        val responseEntity = exceptionController.handleException(servletException)
        val response = responseEntity.body as ErrorResponse

        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.NOT_IMPLEMENTED.value())
        assertThat(response.isSuccess).isFalse()
        assertThat(response.code).isEqualTo("Error.Occurred")
        assertThat(response.message).isEqualTo("Error.Occurred.message(locale=${Locale.KOREAN})")
        assertThat(response.description).isEqualTo("Error.Occurred.description(args=[],locale=${Locale.KOREAN})")
        assertThat(response.errors.size).isEqualTo(1)

        val errorItem = response.errors[0]
        assertThat(errorItem.code).isEqualTo("Error.NotImplemented")
        assertThat(errorItem.message).isEqualTo("Error.NotImplemented.message(locale=${Locale.KOREAN})")
        assertThat(errorItem.description).isEqualTo("Error.NotImplemented.description(args=[],locale=${Locale.KOREAN})")
        assertThat(errorItem.source).isEqualTo("server")
    }

    @Test
    @DisplayName("ValidationExceptionCollector 을 잘 handle 하는 지 테스트")
    fun handleValidationExceptionCollectorTest() {
        val exceptionCollector = ValidationExceptionCollector()

        val exception1 = NullArgumentException("loginId")
        val exception2 = NullArgumentException("password")
        exceptionCollector.addCustomExceptionOrThrow(exception1)
        exceptionCollector.addCustomExceptionOrThrow(exception2)

        val responseEntity = exceptionController.handleValidationExceptionCollector(exceptionCollector)
        val response = responseEntity.body as ErrorResponse

        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(response.isSuccess).isFalse()
        assertThat(response.code).isEqualTo("Error.Occurred")
        assertThat(response.message).isEqualTo("Error.Occurred.message(locale=${Locale.KOREAN})")
        assertThat(response.description).isEqualTo("Error.Occurred.description(args=[],locale=${Locale.KOREAN})")
        assertThat(response.errors.size).isEqualTo(2)

        val errorItem1 = response.errors[0]
        val errorItem2 = response.errors[1]

        assertThat(errorItem1.code).isEqualTo(exception1.code)
        assertThat(errorItem1.message).isEqualTo("${exception1.code}.message(locale=${Locale.KOREAN})")
        assertThat(errorItem1.description).isEqualTo("${exception1.code}.description(args=${exception1.args},locale=${Locale.KOREAN})")
        assertThat(errorItem1.source).isEqualTo(exception1.source)
        assertThat(errorItem2.code).isEqualTo(exception2.code)
        assertThat(errorItem2.message).isEqualTo("${exception2.code}.message(locale=${Locale.KOREAN})")
        assertThat(errorItem2.description).isEqualTo("${exception2.code}.description(args=${exception2.args},locale=${Locale.KOREAN})")
        assertThat(errorItem2.source).isEqualTo(exception2.source)
    }
}
