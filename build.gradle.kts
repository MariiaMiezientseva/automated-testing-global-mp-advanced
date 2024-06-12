import org.gradle.api.tasks.testing.logging.TestLoggingContainer

plugins {
    id("java")
}

group = "group=com.epam.automated.testing.global.mp.advanced"
version = "1.0-SNAPSHOT"

val lombokVersion: String by extra
val testNgVersion: String by extra
val restAssuredVersion: String by extra
val assertJVersion: String by extra
val commonsIoVersion: String by extra
val log4jVersion: String by extra
val testNgEngineVersion: String by extra
val selenideVersion: String by extra
val awaitilityVersion: String by extra
val commonsLangVersion: String by extra

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("commons-io:commons-io:$commonsIoVersion")
    implementation("org.projectlombok:lombok:$lombokVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("com.codeborne:selenide:$selenideVersion")
    implementation("com.codeborne:selenide-core:$selenideVersion")
    implementation("org.apache.commons:commons-lang3:$commonsLangVersion")
    implementation("org.awaitility:awaitility:$awaitilityVersion")
    implementation("com.epam.reportportal:agent-java-testng:5.3.1")
    implementation("com.epam.reportportal:client-java:5.2.13")

    testImplementation("org.testng:testng:$testNgVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("com.codeborne:selenide-testng:$selenideVersion")

    testRuntimeOnly("org.junit.support:testng-engine:$testNgEngineVersion")

    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

fun configureTestLogging(logging: TestLoggingContainer) {
    logging.events = org.gradle.api.tasks.testing.logging.TestLogEvent.values().toSet()
    logging.showStandardStreams = true
    logging.showStackTraces = true
    logging.exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
}

tasks.register("printProperty") {
    doLast {
        val teamcity: Map<*,*> by project
        println("${teamcity["teamcity.build.id"]}")
    }
}

tasks.test {
    environment("RP_USERNAME", System.getenv("RP_USERNAME"))
    environment("RP_PASSWORD", System.getenv("RP_PASSWORD"))
    environment("RP_API_KEY", System.getenv("RP_API_KEY"))
    useTestNG() {
        suites("src/test/resources/suites/testng_test_execution.xml")
        configureTestLogging(testLogging)
    }
}
