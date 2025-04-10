dependencies {
    implementation(Dependencies.SPRING_BOOT_SECURITY.fullName)
    implementation(project(":board-system-member:application-output-port"))
    implementation(project(":board-system-member:domain"))
    implementation(project(":board-system-common:core"))
}
