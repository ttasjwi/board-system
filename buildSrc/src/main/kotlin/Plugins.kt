enum class Plugins(
    val id: String,
    val version: String = "",
) {

    KOTLIN_JVM(id = "org.jetbrains.kotlin.jvm", version = "1.9.25"),
    KOTLIN_SPRING(id = "org.jetbrains.kotlin.plugin.spring", version = "1.9.25"),

    SPRING_BOOT(id = "org.springframework.boot", version = "3.3.4"),
    SPRING_DEPENDENCY_MANAGEMENT(id = "io.spring.dependency-management", version = "1.1.6"),
}
