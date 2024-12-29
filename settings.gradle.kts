rootProject.name = "backend"

pluginManagement {
    val springBootVersion = "3.4.1"
    val springBootDependencyManagerVersion = "1.1.7"
    val lombokVersion = "8.11"

    plugins {
        java
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springBootDependencyManagerVersion

        // Lombok
        id("io.freefair.lombok") version lombokVersion
    }
}
