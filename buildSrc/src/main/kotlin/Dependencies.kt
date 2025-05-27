enum class Dependencies(
    private val groupId: String,
    private val artifactId: String,
    private val version: String? = null,
    private val classifier: String? = null,
) {

    // kotlin
    KOTLIN_JACKSON(groupId = "com.fasterxml.jackson.module", artifactId = "jackson-module-kotlin"),
    KOTLIN_REFLECT(groupId = "org.jetbrains.kotlin", artifactId = "kotlin-reflect"),
    KOTLIN_LOGGING(groupId = "io.github.oshai", artifactId = "kotlin-logging", version = "7.0.5"),
    KOTLIN_TEST_JUNIT_5(groupId="org.jetbrains.kotlin", artifactId = "kotlin-test-junit5"),

    // spring
    SPRING_BOOT_WEB(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-web"),
    SPRING_BOOT_DATA_JPA(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-data-jpa"),
    SPRING_BOOT_DATA_REDIS(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-data-redis"),
    SPRING_BOOT_SECURITY(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-security"),
    SPRING_BOOT_OAUTH2_CLIENT(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-oauth2-client"),
    SPRING_BOOT_MAIL(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-mail"),
    SPRING_BOOT_TEST(groupId = "org.springframework.boot", artifactId = "spring-boot-starter-test"),

    // jackson date time
    JACKSON_DATETIME(groupId = "com.fasterxml.jackson.datatype", artifactId ="jackson-datatype-jsr310"),

    // p6spy
    P6SPY_DATASOURCE_DECORATOR(groupId = "com.github.gavlyukovskiy", artifactId = "p6spy-spring-boot-starter", version = "1.11.0"),

    // mysql
    MYSQL_DRIVER(groupId = "com.mysql", artifactId = "mysql-connector-j"),

    // querydsl
    QUERY_DSL_JPA(groupId = "com.querydsl", artifactId = "querydsl-jpa", version = "5.1.0", classifier = "jakarta"),
    QUERY_DSL_APT(groupId = "com.querydsl", artifactId = "querydsl-apt", version = "5.1.0", classifier = "jakarta"),

    // yaml message
    YAML_RESOURCE_BUNDLE(groupId = "dev.akkinoc.util", artifactId = "yaml-resource-bundle", version = "2.13.1"),

    // email-format-check
    COMMONS_VALIDATOR(groupId="commons-validator", artifactId ="commons-validator" , version="1.9.0"),

    // test
    JUNIT_PLATFORM_LAUNCHER(groupId = "org.junit.platform", artifactId = "junit-platform-launcher"),

    // mockk
    MOCKK(groupId="io.mockk", artifactId="mockk" , version="1.13.17");

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
