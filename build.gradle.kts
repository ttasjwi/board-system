import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id(Plugins.KOTLIN_JVM.id) version Plugins.KOTLIN_JVM.version
    id(Plugins.KOTLIN_SPRING.id) version Plugins.KOTLIN_SPRING.version apply false
    id(Plugins.SPRING_BOOT.id) version Plugins.SPRING_BOOT.version apply false
    id(Plugins.KAPT.id) version Plugins.KAPT.version apply false
    id(Plugins.SPRING_DEPENDENCY_MANAGEMENT.id) version Plugins.SPRING_DEPENDENCY_MANAGEMENT.version
    id(Plugins.JAVA_TEST_FIXTURES.id)
}

group = ProjectProperties.GROUP_NAME

allprojects {
    apply { plugin(Plugins.KOTLIN_JVM.id) }
    apply { plugin(Plugins.KOTLIN_SPRING.id) }
    apply { plugin(Plugins.SPRING_BOOT.id) }
    apply { plugin(Plugins.SPRING_DEPENDENCY_MANAGEMENT.id) }
    apply { plugin(Plugins.JAVA_TEST_FIXTURES.id) }
    apply { plugin(Plugins.KAPT.id) }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(ProjectProperties.JAVA_VERSION)
        }
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(Dependencies.KOTLIN_REFLECT.fullName)
        testImplementation(Dependencies.SPRING_BOOT_TEST.fullName)
        testImplementation(Dependencies.KOTLIN_TEST_JUNIT_5.fullName)
        testImplementation(Dependencies.MOCKK.fullName)
        testRuntimeOnly(Dependencies.JUNIT_PLATFORM_LAUNCHER.fullName)
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()

        // 경고 무시
        // A Java agent has been loaded dynamically...
        // OpenJDK 64-bit Server VM warning: Sharing is only supported for boot loader ...)
        jvmArgs(
            "-XX:+EnableDynamicAgentLoading",
            "-Xshare:off"
        )
    }

    tasks.named<BootJar>("bootJar") {
        enabled = false
    }

    tasks.named<Jar>("jar") {
        enabled = true
    }
}

tasks.named("build") {
    dependsOn(":board-system-app:bootJar")
}
