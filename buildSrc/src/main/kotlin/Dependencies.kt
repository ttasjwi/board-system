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
    SPRING_BOOT_STARTER(groupId = "org.springframework.boot", artifactId = "spring-boot-starter"),
    SPRING_BOOT_WEB(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-web"),
    SPRING_BOOT_DATA_JPA(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-data-jpa"),
    SPRING_BOOT_MAIL(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-mail"),
    SPRING_BOOT_TEST(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-test"),

    // p6spy
    P6SPY_DATASOURCE_DECORATOR(groupId = "com.github.gavlyukovskiy", artifactId = "p6spy-spring-boot-starter", version = "1.9.2"),

    // mysql
    MYSQL_DRIVER(groupId = "com.mysql", artifactId = "mysql-connector-j"),

    // yaml message
    YAML_RESOURCE_BUNDLE(groupId = "dev.akkinoc.util", artifactId = "yaml-resource-bundle", version = "2.13.0"),

    // email-format-check
    COMMONS_VALIDATOR(groupId="commons-validator", artifactId ="commons-validator" , version="1.9.0");


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
