dependencies {
    implementation(project(":board-system-common:core"))

    // email-sender
    implementation(project(":board-system-email-sender"))

    // user
    implementation(project(":board-system-user:user-web-adapter"))
    implementation(project(":board-system-user:user-application-service"))
    implementation(project(":board-system-user:user-database-adapter"))
    implementation(project(":board-system-user:user-redis-adapter"))
    implementation(project(":board-system-user:user-password-adapter"))
    implementation(project(":board-system-user:user-email-format-validate-adapter"))

    // board
    implementation(project(":board-system-board:board-web-adapter"))
    implementation(project(":board-system-board:board-application-service"))
    implementation(project(":board-system-board:board-database-adapter"))

    // article
    implementation(project(":board-system-article:article-web-adapter"))
    implementation(project(":board-system-article:article-application-service"))
    implementation(project(":board-system-article:article-database-adapter"))

    // aop
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // security
    implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.security:spring-security-web")

    implementation(project(":board-system-infrastructure:jwt"))
    implementation(project(":board-system-infrastructure:database-support"))
    implementation(project(":board-system-infrastructure:web-support"))
    implementation(project(":board-system-infrastructure:event-publisher"))
}

tasks.getByName("bootJar") {
    enabled = true
}
