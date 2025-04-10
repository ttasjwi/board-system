dependencies {
    implementation(project(":board-system-common:core"))
    implementation(project(":board-system-common:web-support"))
    implementation(project(":board-system-common:database-support"))

    // event-publisher
    implementation(project(":board-system-common:event-publisher"))
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
}

tasks.getByName("bootJar") {
    enabled = true
}
