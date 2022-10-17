plugins {
    id("java")
    kotlin("jvm") version "1.7.10"
}

group = "com.rylanchord.junitcookbook"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("io.github.serpro69:kotlin-faker:1.11.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}