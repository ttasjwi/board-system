package com.ttasjwi.board.system.common.page

fun calculatePageLimit(page: Long, pageSize: Long, movablePageCount: Long): Long =
    (((page - 1) / movablePageCount) + 1) * pageSize * movablePageCount + 1


fun calculateOffset(page: Long, pageSize: Long): Long =
    (page - 1) * pageSize

