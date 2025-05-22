dependencies {
    implementation(Dependencies.SPRING_BOOT_DATA_REDIS.fullName)
    implementation(Dependencies.KOTLIN_JACKSON.fullName)

    // common
    implementation(project(":board-system-common:core"))
    testImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-common:logger"))
    implementation(project(":board-system-common:data-serializer"))

    // user
    implementation(project(":board-system-user:user-domain"))
    implementation(project(":board-system-user:user-application-output-port"))
    testImplementation(testFixtures(project(":board-system-user:user-domain")))

    // user-oauth2
    implementation("org.springframework.security:spring-security-oauth2-client")
    implementation("jakarta.servlet:jakarta.servlet-api")

    // article-view
    implementation(project(":board-system-article-view:article-view-domain"))
    implementation(project(":board-system-article-view:article-view-application-output-port"))
    testImplementation(testFixtures(project(":board-system-article-view:article-view-domain")))
}
