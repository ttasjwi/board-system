package com.ttasjwi.board.system.member.application.mapper

import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("EmailAvailableQueryMapper: EmailAvailableRequest 를 EmailAvailableQuery 로 변환한다")
class EmailAvailableQueryMapperTest {

    private lateinit var queryMapper: EmailAvailableQueryMapper

    @BeforeEach
    fun setup() {
        queryMapper = EmailAvailableQueryMapper()
    }

    @Nested
    @DisplayName("mapToQuery: 사용자 요청을 애플리케이션 질의로 변환한다.")
    inner class MapToQuery {

        @Test
        @DisplayName("이메일이 누락되면 예외가 발생한다")
        fun testNullEmail() {
            val request = EmailAvailableRequest(email = null)

            val exception = assertThrows<NullArgumentException> { queryMapper.mapToQuery(request) }
            assertThat(exception.source).isEqualTo("email")
        }

        @Test
        @DisplayName("이메일이 누락되지 않았다면 query 가 반환된다")
        fun testSuccess() {
            val request = EmailAvailableRequest(email = "test@gmail.com")
            val query = queryMapper.mapToQuery(request)

            assertThat(query).isNotNull
            assertThat(query.email).isEqualTo(request.email)
        }
    }
}
