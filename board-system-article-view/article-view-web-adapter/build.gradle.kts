dependencies {
    implementation(Dependencies.SPRING_BOOT_WEB.fullName)
    implementation(project(":board-system-common:core"))
    implementation(project(":board-system-article-view:article-view-application-input-port"))

    testImplementation(Dependencies.SPRING_BOOT_SECURITY.fullName)
    testImplementation(project(":board-system-test:restdocs-support"))
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.springframework.boot:spring-boot-starter-aop")
}
