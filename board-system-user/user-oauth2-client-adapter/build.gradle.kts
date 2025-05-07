dependencies{
    implementation(Dependencies.SPRING_BOOT_OAUTH2_CLIENT.fullName)
    implementation(project(":board-system-user:user-application-output-port"))
    implementation(project(":board-system-user:user-domain"))
    implementation(project(":board-system-common:core"))

    testImplementation(Dependencies.SPRING_BOOT_WEB.fullName)
}
