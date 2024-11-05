dependencies {
    implementation(Dependencies.SPRING_BOOT_STARTER.fullName)
    implementation(project(":board-system-domain:domain-member"))
    implementation(project(":board-system-domain:domain-core"))
}
