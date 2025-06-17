package com.ttasjwi.board.system.articleread.domain.mapper

import com.ttasjwi.board.system.articleread.domain.ArticleSummaryPageReadRequest
import com.ttasjwi.board.system.articleread.domain.exception.InvalidArticlePageException
import com.ttasjwi.board.system.articleread.domain.exception.InvalidArticlePageSizeException
import com.ttasjwi.board.system.articleread.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-read-application-service] ArticleSummaryPageReadQueryMapper 테스트 ")
class ArticleSummaryPageReadQueryMapperTest {

    private lateinit var queryMapper: ArticleSummaryPageReadQueryMapper

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        queryMapper = container.articleSummaryPageReadQueryMapper
    }


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val request = ArticleSummaryPageReadRequest(
            boardId = 1L,
            page = 3,
            pageSize = 10
        )

        // when
        val query = queryMapper.mapToQuery(request)

        // then
        assertThat(query.boardId).isEqualTo(request.boardId)
        assertThat(query.page).isEqualTo(request.page)
        assertThat(query.pageSize).isEqualTo(request.pageSize)
    }

    @Test
    @DisplayName("page 가 null 이면 예외 발생")
    fun pageNullTest() {
        // given
        val request = ArticleSummaryPageReadRequest(
            boardId = 1234L,
            page = null,
            pageSize = 10
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            queryMapper.mapToQuery(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(NullArgumentException::class.java)
        assertThat(exceptions[0].source).isEqualTo("page")
    }


    @Test
    @DisplayName("pageSize 가 null 이면 예외 발생")
    fun pageSizeNullTest() {
        // given
        val request = ArticleSummaryPageReadRequest(
            boardId = 1234L,
            page = 1,
            pageSize = null
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            queryMapper.mapToQuery(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(NullArgumentException::class.java)
        assertThat(exceptions[0].source).isEqualTo("pageSize")
    }


    @Test
    @DisplayName("page 가 최소 page 보다 작으면 예외 발생")
    fun invalidPageTest1() {
        // given
        val request = ArticleSummaryPageReadRequest(
            boardId = 1234L,
            page = ArticleSummaryPageReadQueryMapper.MIN_PAGE - 1,
            pageSize = 10L
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            queryMapper.mapToQuery(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(InvalidArticlePageException::class.java)
        assertThat(exceptions[0].source).isEqualTo("page")
        assertThat(exceptions[0].args).containsExactly(
            request.page,
            ArticleSummaryPageReadQueryMapper.MIN_PAGE,
            ArticleSummaryPageReadQueryMapper.MAX_PAGE
        )
    }

    @Test
    @DisplayName("page 가 최대 page 보다 크면 예외 발생")
    fun invalidPageTest2() {
        // given
        val request = ArticleSummaryPageReadRequest(
            boardId = 1234L,
            page = ArticleSummaryPageReadQueryMapper.MAX_PAGE + 1,
            pageSize = 10L
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            queryMapper.mapToQuery(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(InvalidArticlePageException::class.java)
        assertThat(exceptions[0].source).isEqualTo("page")
        assertThat(exceptions[0].args).containsExactly(
            request.page,
            ArticleSummaryPageReadQueryMapper.MIN_PAGE,
            ArticleSummaryPageReadQueryMapper.MAX_PAGE
        )
    }

    @Test
    @DisplayName("pageSize 범위가 최소 pageSize 범위보다 작으면 예외 발생")
    fun invalidPageSizeTest1() {
        // given
        val request = ArticleSummaryPageReadRequest(
            boardId = 1234L,
            page = 1,
            pageSize = ArticleSummaryPageReadQueryMapper.MIN_PAGE_SIZE - 1L
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            queryMapper.mapToQuery(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(InvalidArticlePageSizeException::class.java)
        assertThat(exceptions[0].source).isEqualTo("pageSize")
        assertThat(exceptions[0].args).containsExactly(
            request.pageSize,
            ArticleSummaryPageReadQueryMapper.MIN_PAGE_SIZE,
            ArticleSummaryPageReadQueryMapper.MAX_PAGE_SIZE
        )
    }

    @Test
    @DisplayName("pageSize 범위가 최대 pageSize 범위를 넘으면 예외 발생")
    fun invalidPageSizeTest2() {
        // given
        val request = ArticleSummaryPageReadRequest(
            boardId = 1234L,
            page = 1,
            pageSize = ArticleSummaryPageReadQueryMapper.MAX_PAGE_SIZE + 1L
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            queryMapper.mapToQuery(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(InvalidArticlePageSizeException::class.java)
        assertThat(exceptions[0].source).isEqualTo("pageSize")
        assertThat(exceptions[0].args).containsExactly(
            request.pageSize,
            ArticleSummaryPageReadQueryMapper.MIN_PAGE_SIZE,
            ArticleSummaryPageReadQueryMapper.MAX_PAGE_SIZE
        )
    }
}
