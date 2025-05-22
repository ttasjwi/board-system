plugins {
    id("org.asciidoctor.jvm.convert") version "4.0.4"
}

val asciidoctorExt: Configuration by configurations.creating

dependencies {
    // core
    implementation(project(":board-system-common:core"))
    testImplementation(testFixtures(project(":board-system-common:core")))

    // email-sender
    implementation(project(":board-system-email-sender"))

    // user
    implementation(project(":board-system-user:user-web-adapter"))
    implementation(project(":board-system-user:user-application-service"))
    implementation(project(":board-system-user:user-password-adapter"))
    implementation(project(":board-system-user:user-email-format-validate-adapter"))
    implementation(project(":board-system-user:user-oauth2-client-adapter"))
    testImplementation(testFixtures(project(":board-system-user:user-domain")))
    testImplementation(project(":board-system-user:user-application-output-port"))

    // board
    implementation(project(":board-system-board:board-web-adapter"))
    implementation(project(":board-system-board:board-application-service"))
    testImplementation(testFixtures(project(":board-system-board:board-domain")))
    testImplementation(project(":board-system-board:board-application-output-port"))

    // article
    implementation(project(":board-system-article:article-web-adapter"))
    implementation(project(":board-system-article:article-application-service"))
    testImplementation(testFixtures(project(":board-system-article:article-domain")))
    testImplementation(project(":board-system-article:article-application-output-port"))

    // article-comment
    implementation(project(":board-system-article-comment:article-comment-web-adapter"))
    implementation(project(":board-system-article-comment:article-comment-application-service"))
    testImplementation(project(":board-system-article-comment:article-comment-domain"))
    testImplementation(project(":board-system-article-comment:article-comment-application-input-port"))
    testImplementation(project(":board-system-article-comment:article-comment-application-output-port"))

    // article-like
    implementation(project(":board-system-article-like:article-like-web-adapter"))
    implementation(project(":board-system-article-like:article-like-application-service"))
    testImplementation(project(":board-system-article-like:article-like-application-input-port"))

    // article-view
    implementation(project(":board-system-article-view:article-view-web-adapter"))
    implementation(project(":board-system-article-view:article-view-application-service"))

    // aop
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // security
    implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.security:spring-security-web")

    implementation(project(":board-system-infrastructure:jwt"))
    implementation(project(":board-system-infrastructure:database-adapter"))
    implementation(project(":board-system-infrastructure:redis-adapter"))
    implementation(project(":board-system-infrastructure:web-support"))
    implementation(project(":board-system-infrastructure:event-publisher"))

    // restdocs adoc -> html
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
}

tasks.asciidoctor {
    dependsOn("copySnippets")
    configurations(asciidoctorExt.name)

    doFirst {
        delete {
            file("src/main/resources/static/docs")
        }
    }

    inputs.dir("build/generated-snippets")
    attributes["snippets"] = file("build/generated-snippets")
    baseDirFollowsSourceFile()
}

tasks.register("copySnippets", Copy::class) {
    dependsOn(tasks.test)
    dependsOn(":board-system-user:user-web-adapter:test")
    dependsOn(":board-system-board:board-web-adapter:test")
    dependsOn(":board-system-article:article-web-adapter:test")
    dependsOn(":board-system-article-comment:article-comment-web-adapter:test")
    dependsOn(":board-system-article-like:article-like-web-adapter:test")

    from(file("$rootDir/board-system-user/user-web-adapter/build/generated-snippets"))
    from(file("$rootDir/board-system-board/board-web-adapter/build/generated-snippets"))
    from(file("$rootDir/board-system-article/article-web-adapter/build/generated-snippets"))
    from(file("$rootDir/board-system-article-comment/article-comment-web-adapter/build/generated-snippets"))
    from(file("$rootDir/board-system-article-like/article-like-web-adapter/build/generated-snippets"))
    into(file("build/generated-snippets"))
}

tasks.register("copyHtml", Copy::class) {
    dependsOn(tasks.asciidoctor)
    from(file("build/docs/asciidoc/"))
    into(file("src/main/resources/static/docs"))
}

tasks.build {
    dependsOn("copyHtml")
}

tasks.getByName("bootJar") {
    dependsOn("copyHtml")
    enabled = true
}
