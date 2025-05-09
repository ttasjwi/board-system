package com.ttasjwi.board.system.article.domain.mapper

import com.ttasjwi.board.system.article.domain.ArticlePageReadRequest
import com.ttasjwi.board.system.article.domain.exception.InvalidArticlePageSizeException
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-application-service] ArticlePageReadQueryMapper 테스트 ")
class ArticlePageReadQueryMapperTest {

    private lateinit var queryMapper: ArticlePageReadQueryMapper

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        queryMapper = container.articlePageReadQueryMapper
    }


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val request = ArticlePageReadRequest(
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
    @DisplayName("boardId 가 null 이면 예외 발생")
    fun boardIdNullTest() {
        // given
        val request = ArticlePageReadRequest(
            boardId = null,
            page = 3,
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
        assertThat(exceptions[0].source).isEqualTo("boardId")
    }

    @Test
    @DisplayName("page 가 null 이면 예외 발생")
    fun pageNullTest() {
        // given
        val request = ArticlePageReadRequest(
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
        val request = ArticlePageReadRequest(
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
    @DisplayName("pageSize 범위가 최대 pageSize 범위를 넘으면 예외 발생")
    fun pageSizeRangeTest() {
        // given
        val request = ArticlePageReadRequest(
            boardId = 1234L,
            page = 1,
            pageSize = ArticlePageReadQueryMapper.MAX_PAGE_SIZE + 1L
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
        assertThat(exceptions[0].args).containsExactly(request.pageSize, ArticlePageReadQueryMapper.MAX_PAGE_SIZE)
    }
}
