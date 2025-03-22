plugins {
    `java-library`
    id("io.spring.dependency-management") version "1.1.7"
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

dependencyManagement {
    dependencies {
        //for compatibility with jakarta.activation instead of javax.activation
        dependency("org.jvnet.staxex:stax-ex:1.8.1")
        dependency("org.glassfish.jaxb:jaxb-runtime:2.3.2")
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.36")
    testCompileOnly("org.projectlombok:lombok:1.18.36")

    implementation(project(":"))

    val springVer = "5.3.16"
    val springBootVer = "2.7.18"
    val mockitoVer = "4.0.0"
    val slf4jVer = "2.0.16"

    implementation("org.slf4j:slf4j-simple:$slf4jVer")
    implementation("org.mockito:mockito-core:$mockitoVer")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVer")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVer")
    implementation("org.springframework:spring-context:$springVer")
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("com.h2database:h2:2.3.232")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVer")
}

tasks.test {
    useJUnitPlatform()
}

java {
    targetCompatibility = JavaVersion.VERSION_11
    sourceCompatibility = JavaVersion.VERSION_11
}
