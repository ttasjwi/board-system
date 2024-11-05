dependencies {
    implementation(Dependencies.SPRING_BOOT_STARTER.fullName)

    // api
    implementation(project(":board-system-api:api-core"))
    implementation(project(":board-system-api:api-member"))
    implementation(project(":board-system-api:api-deploy"))

    // event
    implementation(project(":board-system-event:event-publisher-member"))
    implementation(project(":board-system-event:event-consumer-member"))

    // external
    implementation(project(":board-system-external:external-message"))
    implementation(project(":board-system-external:external-db"))
    implementation(project(":board-system-external:external-redis"))
    implementation(project(":board-system-external:external-exception-handle"))
    implementation(project(":board-system-external:external-email-format-checker"))
    implementation(project(":board-system-external:external-email-sender"))
}

tasks.getByName("bootJar") {
    enabled = true
}
