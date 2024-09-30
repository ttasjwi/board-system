dependencies {
    implementation(Dependencies.SPRING_BOOT_STARTER.fullName)
    implementation(project(":board-system-api:api-deploy"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
