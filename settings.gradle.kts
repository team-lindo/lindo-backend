rootProject.name = "backend"

pluginManagement {
    val springBootVersion = "3.4.1"
    val springBootDependencyManagerVersion = "1.1.7"

    plugins {
        java
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springBootDependencyManagerVersion
    }
}
