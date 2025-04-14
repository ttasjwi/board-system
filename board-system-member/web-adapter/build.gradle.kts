dependencies {
    implementation(Dependencies.SPRING_BOOT_WEB.fullName)
    implementation(project(":board-system-common:core"))
    testImplementation(testFixtures(project(":board-system-common:core")))

    implementation(Dependencies.SPRING_BOOT_SECURITY.fullName)
    implementation(Dependencies.SPRING_BOOT_OAUTH2_CLIENT.fullName)
    implementation(project(":board-system-common:data-serializer"))
    implementation(project(":board-system-member:application-input-port"))
    testImplementation(testFixtures(project(":board-system-member:application-input-port")))
}
