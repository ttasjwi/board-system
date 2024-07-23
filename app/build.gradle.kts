dependencies {
    implementation(Dependencies.SPRING_BOOT_WEB.fullName)
    implementation(Dependencies.KOTLIN_JACKSON.fullName)
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
