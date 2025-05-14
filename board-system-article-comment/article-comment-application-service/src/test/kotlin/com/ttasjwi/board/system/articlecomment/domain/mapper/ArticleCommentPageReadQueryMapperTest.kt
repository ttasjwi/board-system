package com.ttasjwi.board.system.articlecomment.domain.mapper

import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentPageReadRequest
import com.ttasjwi.board.system.articlecomment.domain.exception.InvalidArticleCommentPageSizeException
import com.ttasjwi.board.system.articlecomment.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-comment-application-service] ArticleCommentPageReadQueryMapper 테스트 ")
class ArticleCommentPageReadQueryMapperTest {

    private lateinit var queryMapper: ArticleCommentPageReadQueryMapper

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        queryMapper = container.articleCommentPageReadQueryMapper
    }


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val request = ArticleCommentPageReadRequest(
            articleId = 1L,
            page = 3L,
            pageSize = 10L
        )

        // when
        val query = queryMapper.mapToQuery(request)

        // then
        assertThat(query.articleId).isEqualTo(request.articleId)
        assertThat(query.page).isEqualTo(request.page)
        assertThat(query.pageSize).isEqualTo(request.pageSize)
    }

    @Test
    @DisplayName("articleId 가 null 이면 예외 발생")
    fun articleIdNullTest() {
        // given
        val request = ArticleCommentPageReadRequest(
            articleId = null,
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
        assertThat(exceptions[0].source).isEqualTo("articleId")
    }

    @Test
    @DisplayName("page 가 null 이면 예외 발생")
    fun pageNullTest() {
        // given
        val request = ArticleCommentPageReadRequest(
            articleId = 1234L,
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
        val request = ArticleCommentPageReadRequest(
            articleId = 1234L,
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
    @DisplayName("pageSize 범위가 최소 pageSize 범위보다 작으면 예외 발생")
    fun invalidPageSizeTest1() {
        // given
        val request = ArticleCommentPageReadRequest(
            articleId = 1234L,
            page = 1,
            pageSize = ArticleCommentPageReadQueryMapper.MIN_PAGE_SIZE - 1L
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            queryMapper.mapToQuery(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(InvalidArticleCommentPageSizeException::class.java)
        assertThat(exceptions[0].source).isEqualTo("pageSize")
        assertThat(exceptions[0].args).containsExactly(
            request.pageSize,
            ArticleCommentPageReadQueryMapper.MIN_PAGE_SIZE,
            ArticleCommentPageReadQueryMapper.MAX_PAGE_SIZE
        )
    }

    @Test
    @DisplayName("pageSize 범위가 최대 pageSize 범위를 넘으면 예외 발생")
    fun invalidPageSizeTest2() {
        // given
        val request = ArticleCommentPageReadRequest(
            articleId = 1234L,
            page = 1,
            pageSize = ArticleCommentPageReadQueryMapper.MAX_PAGE_SIZE + 1L
        )

        // when
        val exceptionCollector = assertThrows<ValidationExceptionCollector> {
            queryMapper.mapToQuery(request)
        }

        // then
        val exceptions = exceptionCollector.getExceptions()
        assertThat(exceptions.size).isEqualTo(1)
        assertThat(exceptions[0]).isInstanceOf(InvalidArticleCommentPageSizeException::class.java)
        assertThat(exceptions[0].source).isEqualTo("pageSize")
        assertThat(exceptions[0].args).containsExactly(
            request.pageSize,
            ArticleCommentPageReadQueryMapper.MIN_PAGE_SIZE,
            ArticleCommentPageReadQueryMapper.MAX_PAGE_SIZE
        )
    }
}
