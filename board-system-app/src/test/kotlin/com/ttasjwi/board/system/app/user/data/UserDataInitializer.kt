package com.ttasjwi.board.system.app.user.data

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File
import java.time.format.DateTimeFormatter

@Disabled
class UserDataInitializer {

    @Test
    @DisplayName("회원 벌크 삽입 csv 파일 생성")
    fun initialize() {
        val csvFile = File("users.csv")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        csvFile.bufferedWriter().use { writer ->
            for (id in 1L..12_000_000L) {
                val paddedId = id.toString().padStart(8, '0')

                val email = "user${paddedId}@gmail.com"
                val password = "{bcrypt}$2a$10\$glxdATzDUUpvB92yt7OkI.zCPjgn1R1v2uWnQ43gHo.st2t9B9hMy"
                val username = "user${paddedId}"
                val nickname = "user${paddedId}"
                val role = "USER"
                val registeredAt = java.time.LocalDateTime.now().format(formatter)
                writer.write("$id,$email,$password,$username,$nickname,$role,$registeredAt\n")

                if (id % 100000 == 0L) {
                    println("Written $id lines...")
                }
            }
        }
    }
}
