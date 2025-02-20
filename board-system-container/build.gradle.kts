dependencies {
    implementation(Dependencies.SPRING_BOOT_STARTER.fullName)

    // api
    implementation(project(":board-system-api:api-core"))
    implementation(project(":board-system-api:api-member"))
    implementation(project(":board-system-api:api-auth"))
    implementation(project(":board-system-api:api-board"))
    implementation(project(":board-system-api:api-deploy"))

    // event
    implementation(project(":board-system-event:event-publisher-member"))
    implementation(project(":board-system-event:event-consumer-member"))

    // application
    implementation(project(":board-system-application:application-core"))
    implementation(project(":board-system-application:application-member"))
    implementation(project(":board-system-application:application-auth"))
    implementation(project(":board-system-application:application-board"))

    // domain
    implementation(project(":board-system-domain:domain-core"))
    implementation(project(":board-system-domain:domain-member"))
    implementation(project(":board-system-domain:domain-auth"))
    implementation(project(":board-system-domain:domain-board"))

    // external
    implementation(project(":board-system-external:external-message"))
    implementation(project(":board-system-external:external-db"))
    implementation(project(":board-system-external:external-redis"))
    implementation(project(":board-system-external:external-security"))
    implementation(project(":board-system-external:external-exception-handle"))
    implementation(project(":board-system-external:external-email-format-checker"))
    implementation(project(":board-system-external:external-email-sender"))

    testImplementation(Dependencies.SPRING_SECURITY_OAUTH2_CLIENT.fullName)
}

tasks.getByName("bootJar") {
    enabled = true
}
