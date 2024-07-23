enum class Plugins(val id: String, val version: String) {

    KOTLIN_JVM(id = "org.jetbrains.kotlin.jvm", version = "1.9.24"),
    KOTLIN_SPRING(id = "org.jetbrains.kotlin.plugin.spring", version = "1.9.24"),
    KOTLIN_KAPT(id = "org.jetbrains.kotlin.kapt", version = "1.9.24"),
    KOTLIN_JPA(id = "org.jetbrains.kotlin.plugin.jpa", version = "1.9.24"),

    SPRING_BOOT(id= "org.springframework.boot", version = "3.3.2"),
    SPRING_DEPENDENCY_MANAGEMENT(id = "io.spring.dependency-management", version = "1.1.6")

}
