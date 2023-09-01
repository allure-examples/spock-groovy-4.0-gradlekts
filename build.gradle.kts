plugins {
    groovy
}

tasks.withType(Wrapper::class) {
    gradleVersion = "7.5.1"
}

group = "io.qameta.allure.examples"
version = 1.3

val allureVersion = "2.24.0"
val cucumberVersion = "7.13.0"
val aspectJVersion = "1.9.20"
val spockVersion = "2.3-groovy-4.0"

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

tasks.test {
    ignoreFailures = true
    useJUnitPlatform()
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
}

dependencies {
    agent("org.aspectj:aspectjweaver:${aspectJVersion}")

    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-spock2")
    testImplementation("io.qameta.allure:allure-junit-platform")

    // it is important to have spock dependencies to be defined after allure-spock2!!!
    // see https://github.com/allure-framework/allure-java/issues/928#issuecomment-1594596412
    testImplementation(platform("org.spockframework:spock-bom:$spockVersion"))
    testImplementation("org.spockframework:spock-core")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("org.junit.jupiter:junit-jupiter-api")

    testImplementation("org.slf4j:slf4j-simple:1.7.30")
}

repositories {
    mavenCentral()
}
