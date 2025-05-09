package com.ttasjwi.board.system.common.page

import kotlin.math.ceil
import kotlin.math.min

data class PagingInfo
internal constructor(
    val page: Long,
    val pageSize: Long,
    val totalCount: Long,
    val pageGroupSize: Long,
) {

    companion object {

        fun from(page: Long, pageSize: Long, totalCount: Long, pageGroupSize: Long): PagingInfo {
            return PagingInfo(
                page = page,
                pageSize = pageSize,
                totalCount = totalCount,
                pageGroupSize = pageGroupSize,
            )
        }
    }

    private val totalPages: Long = ceil(totalCount / pageSize.toDouble()).toLong()
    private val pageGroupIndex: Long = (page - 1) / pageGroupSize
    val pageGroupStart: Long = pageGroupIndex * pageGroupSize + 1
    val pageGroupEnd: Long = min((pageGroupIndex + 1) * pageGroupSize, totalPages)
    val hasNextPage: Boolean = page < totalPages
    val hasNextPageGroup: Boolean = pageGroupEnd < totalPages
}
