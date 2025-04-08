package com.ttasjwi.board.system.spring.web.exception

import com.ttasjwi.board.system.common.api.ErrorResponse
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import jakarta.servlet.ServletException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.resource.NoResourceFoundException
import java.util.*


@DisplayName("GlobalExceptionController 테스트")
class GlobalExceptionControllerTest {

    private lateinit var exceptionController: GlobalExceptionController

    @BeforeEach
    fun setup() {
        exceptionController = GlobalExceptionController(
            messageResolver = MessageResolverFixture(),
            localeManager = LocaleManagerFixture()
        )
    }


    @Test
    @DisplayName("Exception 및 그 자손은 서버 에러로 취급한다.")
    fun handleExceptionTest() {
        val exception = Exception()

        val responseEntity = exceptionController.handleException(exception)
        val response = responseEntity.body as ErrorResponse

        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        assertThat(response.errors.size).isEqualTo(1)

        val errorItem = response.errors[0]
        assertThat(errorItem.code).isEqualTo("Error.Server")
        assertThat(errorItem.message).isEqualTo("Error.Server.message(locale=${Locale.KOREAN},args=[])")
        assertThat(errorItem.description).isEqualTo("Error.Server.description(locale=${Locale.KOREAN},args=[])")
        assertThat(errorItem.source).isEqualTo("server")
    }

    @Test
    @DisplayName("우리 서비스에서 정의한 CustomException 을 받으면 예외 정보를 기반으로 예외처리한다.")
    fun handleCustomException() {
        val exception = NullArgumentException("email")

        val responseEntity = exceptionController.handleCustomException(exception)
        val response = responseEntity.body as ErrorResponse

        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(response.errors.size).isEqualTo(1)

        val errorItem = response.errors[0]
        assertThat(errorItem.code).isEqualTo(exception.code)
        assertThat(errorItem.message).isEqualTo("${exception.code}.message(locale=${Locale.KOREAN},args=[])")
        assertThat(errorItem.description).isEqualTo("${exception.code}.description(locale=${Locale.KOREAN},args=${exception.args})")
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
        assertThat(response.errors.size).isEqualTo(1)

        val errorItem = response.errors[0]
        assertThat(errorItem.code).isEqualTo("Error.NotImplemented")
        assertThat(errorItem.message).isEqualTo("Error.NotImplemented.message(locale=${Locale.KOREAN},args=[])")
        assertThat(errorItem.description).isEqualTo("Error.NotImplemented.description(locale=${Locale.KOREAN},args=[])")
        assertThat(errorItem.source).isEqualTo("server")
    }

    @Test
    @DisplayName("NoResourceFoundException 처리 테스트")
    fun handleNoResourceFound() {
        val e = NoResourceFoundException(HttpMethod.GET, "fire/punch")

        val responseEntity = exceptionController.handleNoResourceFoundException(e)
        val response = responseEntity.body as ErrorResponse

        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.NOT_FOUND.value())
        assertThat(response.errors.size).isEqualTo(1)

        val errorItem = response.errors[0]
        assertThat(errorItem.code).isEqualTo("Error.ResourceNotFound")
        assertThat(errorItem.message).isEqualTo("Error.ResourceNotFound.message(locale=${Locale.KOREAN},args=[])")
        assertThat(errorItem.description).isEqualTo("Error.ResourceNotFound.description(locale=${Locale.KOREAN},args=[${e.httpMethod.name()}, /${e.resourcePath}])")
        assertThat(errorItem.source).isEqualTo("httpMethod,resourcePath")
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
        assertThat(response.errors.size).isEqualTo(2)

        val errorItem1 = response.errors[0]
        val errorItem2 = response.errors[1]

        assertThat(errorItem1.code).isEqualTo(exception1.code)
        assertThat(errorItem1.message).isEqualTo("${exception1.code}.message(locale=${Locale.KOREAN},args=[])")
        assertThat(errorItem1.description).isEqualTo("${exception1.code}.description(locale=${Locale.KOREAN},args=${exception1.args})")
        assertThat(errorItem1.source).isEqualTo(exception1.source)
        assertThat(errorItem2.code).isEqualTo(exception2.code)
        assertThat(errorItem2.message).isEqualTo("${exception2.code}.message(locale=${Locale.KOREAN},args=[])")
        assertThat(errorItem2.description).isEqualTo("${exception2.code}.description(locale=${Locale.KOREAN},args=${exception2.args})")
        assertThat(errorItem2.source).isEqualTo(exception2.source)
    }
}
