plugins {
    kotlin("jvm") version "2.2.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.google.ortools:ortools-java:9.11.4210")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}