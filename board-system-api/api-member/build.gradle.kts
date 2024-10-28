dependencies {
    implementation(project(":board-system-api:api-core"))
    implementation(project(":board-system-application:application-member"))
    implementation(Dependencies.SPRING_BOOT_WEB.fullName)
    implementation(Dependencies.KOTLIN_JACKSON.fullName)
}
