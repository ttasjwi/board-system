package com.ttasjwi.board.system.app.board.data

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Disabled
class BoardDataInitializer {

    @Test
    @DisplayName("게시판 벌크 삽입 csv 파일 생성")
    fun initialize() {
        val csvFile = File("boards.csv")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        csvFile.bufferedWriter().use { writer ->
            for (id in 1L..500L) {
                val paddedId = id.toString().padStart(8, '0')

                val name = "board${paddedId}"
                val description = "The board of $name"
                val managerId = id
                val slug = "board${paddedId}"
                val createdAt = LocalDateTime.now().format(formatter)

                writer.write("$id,$name,$description,$managerId,$slug,$createdAt\n")

                if (id % 10 == 0L) {
                    println("Written $id lines...")
                }
            }
        }
    }
}
