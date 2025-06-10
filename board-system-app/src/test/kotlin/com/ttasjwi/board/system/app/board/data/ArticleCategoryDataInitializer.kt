package com.ttasjwi.board.system.app.board.data

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.BufferedWriter
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Disabled
class ArticleCategoryDataInitializer {

    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }

    @Test
    @DisplayName("게시글 카테고리 벌크 삽입 csv 파일 생성")
    fun initialize() {
        val csvFile = File("article_categories.csv")
        csvFile.bufferedWriter().use { writer ->
            for (boardId in 1L..500L) {
                makeGeneralCategory(writer, boardId, (boardId - 1) * 4 + 1)
                makeQuestionCategory(writer, boardId, (boardId - 1) * 4 + 2)
                makeTipCategory(writer, boardId, (boardId - 1) * 4 + 3)
                makeNoticeCategory(writer, boardId, (boardId - 1) * 4 + 4)
            }
        }
    }

    private fun makeGeneralCategory(writer: BufferedWriter, boardId: Long, articleCategoryId: Long) {
        val name = "일반"
        val slug = "general"
        val allowWrite = 1
        val allowSelfEditDelete = 1
        val allowComment = 1
        val allowLike = 1
        val allowDislike = 1
        val createdAt = LocalDateTime.now().format(formatter)

        writer.write("$articleCategoryId,$name,$slug,$boardId,$allowWrite,$allowSelfEditDelete,$allowComment,$allowLike,$allowDislike,$createdAt\n")
        if (articleCategoryId % 100 == 0L) {
            println("Written $articleCategoryId lines...")
        }
    }

    private fun makeQuestionCategory(writer: BufferedWriter, boardId: Long, articleCategoryId: Long) {
        val name = "질문"
        val slug = "question"
        val allowWrite = 1
        val allowSelfEditDelete = 0
        val allowComment = 1
        val allowLike = 1
        val allowDislike = 1
        val createdAt = LocalDateTime.now().format(formatter)

        writer.write("$articleCategoryId,$name,$slug,$boardId,$allowWrite,$allowSelfEditDelete,$allowComment,$allowLike,$allowDislike,$createdAt\n")
        if (articleCategoryId % 100 == 0L) {
            println("Written $articleCategoryId lines...")
        }
    }

    private fun makeTipCategory(writer: BufferedWriter, boardId: Long, articleCategoryId: Long) {
        val name = "정보"
        val slug = "tip"
        val allowWrite = 1
        val allowSelfEditDelete = 1
        val allowComment = 1
        val allowLike = 1
        val allowDislike = 0
        val createdAt = LocalDateTime.now().format(formatter)

        writer.write("$articleCategoryId,$name,$slug,$boardId,$allowWrite,$allowSelfEditDelete,$allowComment,$allowLike,$allowDislike,$createdAt\n")
        if (articleCategoryId % 100 == 0L) {
            println("Written $articleCategoryId lines...")
        }
    }

    private fun makeNoticeCategory(writer: BufferedWriter, boardId: Long, articleCategoryId: Long) {
        val name = "공지"
        val slug = "notice"
        val allowWrite = 0
        val allowSelfEditDelete = 0
        val allowComment = 0
        val allowLike = 0
        val allowDislike = 0
        val createdAt = LocalDateTime.now().format(formatter)

        writer.write("$articleCategoryId,$name,$slug,$boardId,$allowWrite,$allowSelfEditDelete,$allowComment,$allowLike,$allowDislike,$createdAt\n")
        if (articleCategoryId % 100 == 0L) {
            println("Written $articleCategoryId lines...")
        }
    }
}
