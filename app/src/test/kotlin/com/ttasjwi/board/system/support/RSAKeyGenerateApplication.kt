package com.ttasjwi.board.system.support

import java.io.File
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.util.Base64

const val DIRECTORY = "./app/src/main/resources/rsa-keypair"

fun main() {
    // 디렉토리 생성
    val directory = File(DIRECTORY)
    if (!directory.exists()) {
        directory.mkdirs()
    }

    try {
        // RSA 키 쌍 생성
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048) // RSA 2048 비트 키 생성
        val keyPair: KeyPair = keyPairGenerator.generateKeyPair()
        val publicKey = keyPair.public
        val privateKey = keyPair.private

        // Base64 인코딩
        val base64Encoder = Base64.getEncoder()
        val publicKeyEncoded = base64Encoder.encodeToString(publicKey.encoded)
        val privateKeyEncoded = base64Encoder.encodeToString(privateKey.encoded)

        // PEM 형식에 맞게 64자마다 개행 추가
        val publicKeyPem = formatAsPem(publicKeyEncoded, "PUBLIC KEY")
        val privateKeyPem = formatAsPem(privateKeyEncoded, "PRIVATE KEY")

        // 키를 파일로 저장
        File("${DIRECTORY}/public_key_xxx.pem").writeText(publicKeyPem)
        File("${DIRECTORY}/private_key_xxx.pem").writeText(privateKeyPem)

        println("RSA 키 쌍이 생성되어 파일에 저장되었습니다.")
    } catch (e: Exception) {
        println("키 생성 및 저장 중 오류가 발생했습니다: ${e.message}")
        e.printStackTrace()
    }
}

// PEM 형식에 맞게 64자마다 개행
fun formatAsPem(encodedKey: String, keyType: String): String {
    val lineLength = 64
    val sb = StringBuilder("-----BEGIN $keyType-----\n")
    for (i in encodedKey.indices step lineLength) {
        val endIndex = (i + lineLength).coerceAtMost(encodedKey.length)
        sb.append(encodedKey.substring(i, endIndex)).append("\n")
    }
    sb.append("-----END $keyType-----")
    return sb.toString()
}
