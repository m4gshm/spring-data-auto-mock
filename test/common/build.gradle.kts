plugins {
    `java-library`
//    id("io.spring.dependency-management") version "1.1.7"
}
group = "github.m4gshm"

repositories {
    mavenCentral()
}

configurations.annotationProcessor {
    extendsFrom(configurations.compileOnly.get())
}

configurations.testAnnotationProcessor {
    extendsFrom(configurations.testCompileOnly.get())
}


dependencies {
    compileOnly("org.projectlombok:lombok:1.18.36")
    testCompileOnly("org.projectlombok:lombok:1.18.36")

    implementation(project(":"))

    val springVer = "5.3.16"
    val springBootVer = "2.7.18"

    implementation("org.springframework.data:spring-data-commons:$springBootVer")
    implementation("org.springframework:spring-context:$springVer")
}

tasks.test {
    useJUnitPlatform()
}

java {
    targetCompatibility = JavaVersion.VERSION_11
    sourceCompatibility = JavaVersion.VERSION_11
}
