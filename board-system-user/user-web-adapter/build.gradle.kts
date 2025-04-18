dependencies {
    implementation(Dependencies.SPRING_BOOT_WEB.fullName)
    implementation(project(":board-system-common:core"))
    implementation(Dependencies.SPRING_BOOT_SECURITY.fullName)
    implementation(Dependencies.SPRING_BOOT_OAUTH2_CLIENT.fullName)
    implementation(project(":board-system-common:data-serializer"))
    implementation(project(":board-system-user:user-application-input-port"))

    testImplementation(testFixtures(project(":board-system-common:core")))
    testImplementation(testFixtures(project(":board-system-user:user-application-input-port")))
    testImplementation(testFixtures(project(":board-system-article:article-application-input-port")))
    testImplementation(project(":board-system-test:restdocs-support"))
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.springframework.boot:spring-boot-starter-aop")
}
