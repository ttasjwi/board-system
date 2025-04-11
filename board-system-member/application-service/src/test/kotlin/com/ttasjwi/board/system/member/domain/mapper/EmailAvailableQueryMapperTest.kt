package com.ttasjwi.board.system.member.domain.mapper

import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.locale.fixture.LocaleResolverFixture
import com.ttasjwi.board.system.member.domain.EmailAvailableRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import java.util.*

@DisplayName("EmailAvailableQueryMapper: EmailAvailableRequest 를 EmailAvailableQuery 로 변환한다")
class EmailAvailableQueryMapperTest {

    private lateinit var queryMapper: EmailAvailableQueryMapper
    private lateinit var localeResolverFixture: LocaleResolverFixture

    @BeforeEach
    fun setup() {
        localeResolverFixture = LocaleResolverFixture()
        queryMapper = EmailAvailableQueryMapper(
            localeResolver = localeResolverFixture
        )
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
            localeResolverFixture.changeLocale(Locale.GERMANY)

            val request = EmailAvailableRequest(email = "test@gmail.com")
            val query = queryMapper.mapToQuery(request)

            assertThat(query).isNotNull
            assertThat(query.email).isEqualTo(request.email)
            assertThat(query.locale).isEqualTo(Locale.GERMANY)
        }
    }
}
