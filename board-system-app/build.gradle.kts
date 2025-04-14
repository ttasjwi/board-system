dependencies {
    implementation(project(":board-system-common:core"))

    // email-sender
    implementation(project(":board-system-email-sender"))

    // member
    implementation(project(":board-system-member:web-adapter"))
    implementation(project(":board-system-member:application-service"))
    implementation(project(":board-system-member:database-adapter"))
    implementation(project(":board-system-member:redis-adapter"))
    implementation(project(":board-system-member:password-adapter"))
    implementation(project(":board-system-member:email-format-validate-adapter"))

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
