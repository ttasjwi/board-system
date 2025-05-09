package com.ttasjwi.board.system.common.page

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[common-core] PagingInfo: 페이징 정보 계산 클래스")
class PagingInfoTest {

    @Test
    @DisplayName("page = 1, pageSize = 10, totalCount = 101 일때의 페이징 정보")
    fun test1() {
        // given
        val page = 1L
        val pageSize = 10L
        val totalCount = 101L
        val pageGroupSize = 10L

        // when
        val pagingInfo = PagingInfo.from(
            page = page,
            pageSize = pageSize,
            totalCount = totalCount,
            pageGroupSize = pageGroupSize
        )

        // then
        assertThat(pagingInfo.pageGroupStart).isEqualTo(1L)
        assertThat(pagingInfo.pageGroupEnd).isEqualTo(10L)
        assertThat(pagingInfo.hasNextPage).isTrue()
        assertThat(pagingInfo.hasNextPageGroup).isTrue()
    }

    @Test
    @DisplayName("page = 7, pageSize = 10, totalCount = 100 일때의 페이징 정보")
    fun test2() {
        // given
        val page = 7L
        val pageSize = 10L
        val totalCount = 100L
        val pageGroupSize = 10L

        // when
        val pagingInfo = PagingInfo.from(
            page = page,
            pageSize = pageSize,
            totalCount = totalCount,
            pageGroupSize = pageGroupSize
        )

        // then
        assertThat(pagingInfo.pageGroupStart).isEqualTo(1L)
        assertThat(pagingInfo.pageGroupEnd).isEqualTo(10L)
        assertThat(pagingInfo.hasNextPage).isTrue()
        assertThat(pagingInfo.hasNextPageGroup).isFalse()
    }

    @Test
    @DisplayName("page = 7, pageSize = 10, totalCount = 65 일때의 페이징 정보")
    fun test3() {
        // given
        val page = 7L
        val pageSize = 10L
        val totalCount = 65L
        val pageGroupSize = 10L

        // when
        val pagingInfo = PagingInfo.from(
            page = page,
            pageSize = pageSize,
            totalCount = totalCount,
            pageGroupSize = pageGroupSize
        )

        // then
        assertThat(pagingInfo.pageGroupStart).isEqualTo(1L)
        assertThat(pagingInfo.pageGroupEnd).isEqualTo(7L)
        assertThat(pagingInfo.hasNextPage).isFalse()
        assertThat(pagingInfo.hasNextPageGroup).isFalse()
    }
}
