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
val commonIoVersion: String by extra
val log4jVersion: String by extra
val rpJavaClient: String by extra
val rpJavaAgent: String by extra
val testNgEngineVersion: String by extra

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("commons-io:commons-io:$commonIoVersion")
    implementation("io.rest-assured:rest-assured:$restAssuredVersion")
    implementation("org.hamcrest:hamcrest-all:1.3")
    implementation("org.projectlombok:lombok:$lombokVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")

    testImplementation("com.epam.reportportal:client-java:$rpJavaClient")
    testImplementation("com.epam.reportportal:agent-java-testng:$rpJavaAgent")
    testImplementation("org.testng:testng:$testNgVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")

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

tasks.test {
    useTestNG() {
        configureTestLogging(testLogging)
        maxParallelForks = 1
    }
}