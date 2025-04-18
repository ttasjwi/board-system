dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api")
    compileOnly("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.security:spring-security-test")
    api("org.springframework.restdocs:spring-restdocs-mockmvc")
    implementation(project(":board-system-common:data-serializer"))
    api(project(":board-system-infrastructure:web-support"))
    api(testFixtures(project(":board-system-common:core")))
    api(testFixtures(project(":board-system-common:token")))
}
