import org.gradle.api.tasks.testing.logging.TestLoggingContainer

plugins {
    id("java")
    id("io.qameta.allure-report") version "2.11.2"
}

apply(plugin = "io.qameta.allure-report")

group = "group=com.epam.automated.testing.global.mp.advanced"
version = "1.0-SNAPSHOT"

val lombokVersion: String by extra
val junitVersion: String by extra
val junit5Version: String by extra
val restAssuredVersion: String by extra
val assertJVersion: String by extra
val commonIoVersion: String by extra
val log4jVersion: String by extra
val rpJavaClient: String by extra
val rpJavaAgent: String by extra
val dataProviderVersion: String by extra
val allureReportPluginVersion: String by extra

repositories {
    mavenLocal()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation("commons-io:commons-io:$commonIoVersion")
    implementation("io.rest-assured:rest-assured:$restAssuredVersion")
    implementation("org.hamcrest:hamcrest-all:1.3")
    implementation("org.projectlombok:lombok:$lombokVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("com.github.hemanthsridhar:testng-ext-dataprovider:$dataProviderVersion")

    testImplementation("com.epam.reportportal:client-java:$rpJavaClient")
    testImplementation("com.epam.reportportal:agent-java-junit5:$rpJavaAgent")

    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")

    testImplementation("io.qameta.allure.gradle.report:allure-report-plugin:$allureReportPluginVersion")
    testImplementation("io.qameta.allure:allure-junit5:$junit5Version")

    testImplementation("org.assertj:assertj-core:$assertJVersion")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

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
    useJUnitPlatform() {
        configureTestLogging(testLogging)
    }
    maxParallelForks = Runtime.getRuntime().availableProcessors()
}
