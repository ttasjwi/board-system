enum class Dependencies(
    private val groupId: String,
    private val artifactId: String,
    private val version: String? = null,
    private val classifier: String? = null,
) {

    // kotlin
    KOTLIN_JACKSON(groupId = "com.fasterxml.jackson.module", artifactId = "jackson-module-kotlin"),
    KOTLIN_REFLECT(groupId = "org.jetbrains.kotlin", artifactId = "kotlin-reflect"),
    KOTLIN_LOGGING(groupId = "io.github.oshai", artifactId = "kotlin-logging", version = "7.0.0"),

    // spring
    SPRING_BOOT_WEB(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-web"),
    SPRING_BOOT_SECURITY(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-security"),
    SPRING_BOOT_TEST(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-test"),
    SPRING_BOOT_DATA_JPA(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-data-jpa"),
    SPRING_BOOT_ACTUATOR(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-actuator"),

    // mysql
    MYSQL_DRIVER(groupId = "com.mysql", artifactId = "mysql-connector-java"),

    // p6spy
    P6SPY(groupId="com.github.gavlyukovskiy", artifactId = "p6spy-spring-boot-starter", version= "1.9.1"),

    // querydsl
    QUERYDSL_JPA(groupId = "com.querydsl", artifactId = "querydsl-jpa", version = "5.0.0", classifier = "jakarta"),
    QUERYDSL_PROCESSOR(groupId = "com.querydsl", artifactId = "querydsl-apt", version = "5.0.0", classifier = "jakarta"),

    // yaml-resource
    YAML_RESOURCE_BUNDLE(groupId = "dev.akkinoc.util", artifactId = "yaml-resource-bundle", version = "2.12.3");

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
