dependencies {
    implementation(Dependencies.SPRING_BOOT_SECURITY.fullName)
    implementation(project(":board-system-user:user-application-output-port"))
    implementation(project(":board-system-user:user-domain"))
    implementation(project(":board-system-common:core"))
}
