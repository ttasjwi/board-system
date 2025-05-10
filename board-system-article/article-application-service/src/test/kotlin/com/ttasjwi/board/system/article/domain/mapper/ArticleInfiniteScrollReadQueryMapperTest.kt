package com.ttasjwi.board.system.article.domain.mapper

import com.ttasjwi.board.system.article.domain.ArticleInfiniteScrollReadRequest
import com.ttasjwi.board.system.article.domain.exception.InvalidArticlePageSizeException
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-application-service] ArticleInfiniteScrollReadQueryMapper 테스트 ")
class ArticleInfiniteScrollReadQueryMapperTest {

    private lateinit var queryMapper: ArticleInfiniteScrollReadQueryMapper

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        queryMapper = container.articleInfiniteScrollReadQueryMapper
    }


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val request = ArticleInfiniteScrollReadRequest(
            boardId = 1L,
            pageSize = 10L,
            lastArticleId = 7L
        )

        // when
        val query = queryMapper.mapToQuery(request)

        // then
        assertThat(query.boardId).isEqualTo(request.boardId)
        assertThat(query.pageSize).isEqualTo(request.pageSize)
        assertThat(query.lastArticleId).isEqualTo(request.lastArticleId)
    }

    @Test
    @DisplayName("boardId 가 null 이면 예외 발생")
    fun boardIdNullTest() {
        // given
        val request = ArticleInfiniteScrollReadRequest(
            boardId = null,
            pageSize = 10L,
            lastArticleId = 7L
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
    @DisplayName("pageSize 가 null 이면 예외 발생")
    fun pageSizeNullTest() {
        // given
        val request = ArticleInfiniteScrollReadRequest(
            boardId = 1L,
            pageSize = null,
            lastArticleId = 7L
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
    @DisplayName("pageSize 범위가 최소 pageSize 범위보다 작으면 예외 발생")
    fun invalidPageSizeTest1() {
        // given
        val request = ArticleInfiniteScrollReadRequest(
            boardId = 1234L,
            pageSize = ArticleInfiniteScrollReadQueryMapper.MIN_PAGE_SIZE - 1L,
            lastArticleId = 7L
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
            ArticleInfiniteScrollReadQueryMapper.MIN_PAGE_SIZE,
            ArticleInfiniteScrollReadQueryMapper.MAX_PAGE_SIZE
        )
    }


    @Test
    @DisplayName("pageSize 범위가 최대 pageSize 범위를 넘으면 예외 발생")
    fun invalidPageSizeTest2() {
        // given
        val request = ArticleInfiniteScrollReadRequest(
            boardId = 1234L,
            pageSize = ArticleInfiniteScrollReadQueryMapper.MAX_PAGE_SIZE + 1L,
            lastArticleId = 7L
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
            ArticleInfiniteScrollReadQueryMapper.MIN_PAGE_SIZE,
            ArticleInfiniteScrollReadQueryMapper.MAX_PAGE_SIZE
        )
    }
}
