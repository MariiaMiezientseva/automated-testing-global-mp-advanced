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
val guavaVersion: String by extra

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
    implementation("com.google.guava:guava:$guavaVersion")
    implementation("org.apache.commons:commons-lang3:$commonsLangVersion")
    implementation("org.awaitility:awaitility:$awaitilityVersion")

    testImplementation("org.testng:testng:$testNgVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("com.codeborne:selenide-testng:$selenideVersion")

    testRuntimeOnly("org.junit.support:testng-engine:$testNgEngineVersion")

    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}
//uncomment following code for testing
//fun configureTestLogging(logging: TestLoggingContainer) {
//    logging.events = org.gradle.api.tasks.testing.logging.TestLogEvent.values().toSet()
//    logging.showStandardStreams = true
//    logging.showStackTraces = true
//    logging.exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
//}
//
//tasks.test {
//    environment("RP_USERNAME", "")
//    environment("RP_PASSWORD", "")
//    useTestNG() {
//        suites("src/test/resources/testng_test_execution.xml")
//        configureTestLogging(testLogging)
//    }
//}
