dependencies {
    implementation(Dependencies.SPRING_BOOT_DATA_REDIS.fullName)
    implementation("org.springframework.security:spring-security-oauth2-client")
    implementation("jakarta.servlet:jakarta.servlet-api")
    implementation(project(":board-system-common:core"))
    implementation(project(":board-system-common:logger"))
    implementation(project(":board-system-common:data-serializer"))
    implementation(project(":board-system-user:user-domain"))
    implementation(project(":board-system-user:user-application-output-port"))

    implementation(Dependencies.KOTLIN_JACKSON.fullName)
    testImplementation(testFixtures(project(":board-system-common:core")))
    testImplementation(testFixtures(project(":board-system-user:user-domain")))
}
