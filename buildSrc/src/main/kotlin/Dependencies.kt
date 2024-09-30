enum class Dependencies(
    private val groupId: String,
    private val artifactId: String,
    private val version: String? = null,
    private val classifier: String? = null,
) {

    // kotlin
    KOTLIN_JACKSON(groupId = "com.fasterxml.jackson.module", artifactId = "jackson-module-kotlin"),
    KOTLIN_REFLECT(groupId = "org.jetbrains.kotlin", artifactId = "kotlin-reflect"),

    // spring
    SPRING_BOOT_STARTER(groupId = "org.springframework.boot", artifactId = "spring-boot-starter"),
    SPRING_BOOT_WEB(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-web"),


    SPRING_BOOT_TEST(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-test");

    val fullName: String
        get() {
            if (version == null) {
                return "$groupId:$artifactId"
            }
            if (classifier == null) {
                return "$groupId:$artifactId:$version"
            }
            return "$groupId:$artifactId:$version:$classifier"
        }

}
