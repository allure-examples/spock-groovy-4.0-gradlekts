plugins {
    groovy
}

tasks.withType(Wrapper::class) {
    gradleVersion = "8.5"
}

group = "com.example.spock"
version = "1.0-SNAPSHOT"

val allureVersion = "2.25.0"
val aspectJVersion = "1.9.21"
val groovyVersion = "4.0.16"
val spockVersion = "2.3-groovy-4.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

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
    agent("org.aspectj:aspectjweaver:$aspectJVersion")
    
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-spock2")
    
    // it is important to have spock dependencies to be defined after allure-spock2!!!
    // see https://github.com/allure-framework/allure-java/issues/928#issuecomment-1594596412
    testImplementation(platform("org.spockframework:spock-bom:$spockVersion"))
    testImplementation("org.spockframework:spock-core")
    
    testImplementation(platform("org.apache.groovy:groovy-bom:$groovyVersion"))

    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("org.slf4j:slf4j-simple:2.0.9")
}

repositories {
    mavenCentral()
}
