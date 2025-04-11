package com.ttasjwi.board.system.board.domain.service

interface BoardDescriptionManager {
    fun create(boardDescription: String): Result<String>
}
