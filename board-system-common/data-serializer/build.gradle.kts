dependencies {
    implementation(Dependencies.KOTLIN_JACKSON.fullName)
    implementation(Dependencies.JACKSON_DATETIME.fullName)

    testImplementation(project(":board-system-common:core"))
}
