package com.ttasjwi.board.system.global.dataserializer

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object DataSerializer {

    private val objectMapper by lazy {
        jacksonObjectMapper()
            .registerModule(JavaTimeModule()) /// Java 8+ 날짜/시간(`java.time` 패키지) 직렬화 및 역직렬화 지원
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)  // JSON에 없는 필드가 있어도 역직렬화 실패하지 않음
            .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true) // 날짜 직렬화 시 시간대 정보 포함
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false) // 날짜를 ISO 8601 문자열로 직렬화
    }

    fun serialize(obj: Any): String = objectMapper.writeValueAsString(obj)

    fun <T> deserialize(data: String, clazz: Class<T>): T = objectMapper.readValue(data, clazz)
}
