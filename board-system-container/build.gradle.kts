dependencies {
    implementation(Dependencies.SPRING_BOOT_STARTER.fullName)

    // api
    implementation(project(":board-system-api:api-deploy"))

    // external
    implementation(project(":board-system-external:external-message"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
